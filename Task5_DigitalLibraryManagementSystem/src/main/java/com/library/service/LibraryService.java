package com.library.service;

import com.library.model.*;
import com.library.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Central service holding all library business rules: authentication,
 * catalogue management, issuing/returning books, fines, and reservations.
 */
@Service
public class LibraryService {

    @Autowired private UserRepository userRepository;
    @Autowired private BookRepository bookRepository;
    @Autowired private LoanRepository loanRepository;
    @Autowired private ReservationRepository reservationRepository;
    @Autowired private ContactMessageRepository contactMessageRepository;

    /** Seed an admin account and a few demo books on first startup. */
    @PostConstruct
    public void seedData() {
        if (userRepository.count() == 0) {
            userRepository.save(new User("admin", "admin123", "Library Admin", "admin@library.local", User.Role.ADMIN));
            userRepository.save(new User("member", "member123", "Demo Member", "member@library.local", User.Role.USER));
        }
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("Clean Code", "Robert C. Martin", "9780132350884", "Software Engineering", 3));
            bookRepository.save(new Book("Effective Java", "Joshua Bloch", "9780134685991", "Java", 2));
            bookRepository.save(new Book("The Pragmatic Programmer", "Andrew Hunt", "9780135957059", "Software Engineering", 2));
            bookRepository.save(new Book("Introduction to Algorithms", "Thomas H. Cormen", "9780262046305", "Computer Science", 1));
            bookRepository.save(new Book("1984", "George Orwell", "9780451524935", "Fiction", 4));
        }
    }

    // ---------- Auth ----------

    public Optional<User> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password));
    }

    public boolean register(String username, String password, String fullName, String email) {
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        userRepository.save(new User(username, password, fullName, email, User.Role.USER));
        return true;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<User> getAllMembers() {
        return userRepository.findAll().stream().filter(u -> !u.isAdmin()).toList();
    }

    // ---------- Catalogue (Admin) ----------

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public Book addBook(String title, String author, String isbn, String category, int quantity) {
        return bookRepository.save(new Book(title, author, isbn, category, quantity));
    }

    public void updateBook(Long id, String title, String author, String isbn, String category, int quantity) {
        Book book = getBook(id);
        int issuedCopies = book.getQuantity() - book.getAvailableQuantity();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setCategory(category);
        book.setQuantity(quantity);
        // Keep availableQuantity consistent with however many copies are currently issued.
        book.setAvailableQuantity(Math.max(quantity - issuedCopies, 0));
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // ---------- Catalogue browsing (User) ----------

    public List<Book> browseByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }

    public List<Book> search(String query) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
    }

    // ---------- Issuing / Returning ----------

    @Transactional
    public String issueBook(Long bookId, User user) {
        Book book = getBook(bookId);
        if (book.getAvailableQuantity() <= 0) {
            return "This book is not currently available.";
        }
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        bookRepository.save(book);
        loanRepository.save(new Loan(book, user, LocalDate.now()));
        return null; // null = success
    }

    @Transactional
    public String returnBook(Long loanId, User user) {
        Loan loan = loanRepository.findById(loanId).orElseThrow();
        if (!loan.getUser().getId().equals(user.getId())) {
            return "You can only return your own loans.";
        }
        if (loan.isReturned()) {
            return "This book has already been returned.";
        }
        loan.setReturnDate(LocalDate.now());

        if (LocalDate.now().isAfter(loan.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), LocalDate.now());
            loan.setFineAmount(daysLate * Loan.FINE_PER_DAY);
        }
        loanRepository.save(loan);

        Book book = loan.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        bookRepository.save(book);

        return null;
    }

    public List<Loan> getLoansForUser(User user) {
        return loanRepository.findByUserOrderByIssueDateDesc(user);
    }

    public List<Loan> getAllIssuedLoans() {
        return loanRepository.findByReturnDateIsNull();
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    // ---------- Fines ----------

    public List<Loan> getUnpaidFines() {
        return loanRepository.findByFinePaidFalseAndFineAmountGreaterThan(0.0);
    }

    public void markFinePaid(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow();
        loan.setFinePaid(true);
        loanRepository.save(loan);
    }

    // ---------- Reservations (Advance Booking) ----------

    @Transactional
    public String reserveBook(Long bookId, User user) {
        Book book = getBook(bookId);
        if (book.getAvailableQuantity() > 0) {
            return "This book is currently available — please issue it directly instead of reserving.";
        }
        reservationRepository.save(new Reservation(book, user, LocalDate.now()));
        return null;
    }

    public List<Reservation> getReservationsForUser(User user) {
        return reservationRepository.findByUserOrderByReservedDateDesc(user);
    }

    public List<Reservation> getAllActiveReservations() {
        return reservationRepository.findByStatus(Reservation.Status.ACTIVE);
    }

    // ---------- Contact / Query ----------

    public void submitContactMessage(String name, String email, String message) {
        contactMessageRepository.save(new ContactMessage(name, email, message));
    }

    public List<ContactMessage> getAllContactMessages() {
        return contactMessageRepository.findAll();
    }

    public void resolveContactMessage(Long id) {
        ContactMessage msg = contactMessageRepository.findById(id).orElseThrow();
        msg.setResolved(true);
        contactMessageRepository.save(msg);
    }
}
