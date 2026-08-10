package com.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Records a single book being issued to a user, its due date, and (once
 * returned) the return date and any overdue fine.
 */
@Entity
@Table(name = "loans")
public class Loan {

    public static final int LOAN_PERIOD_DAYS = 14;
    public static final double FINE_PER_DAY = 5.0; // currency units per day overdue

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    private LocalDate returnDate; // null while still issued

    @Column(nullable = false)
    private double fineAmount = 0.0;

    @Column(nullable = false)
    private boolean finePaid = false;

    public Loan() {}

    public Loan(Book book, User user, LocalDate issueDate) {
        this.book = book;
        this.user = user;
        this.issueDate = issueDate;
        this.dueDate = issueDate.plusDays(LOAN_PERIOD_DAYS);
    }

    public boolean isReturned() { return returnDate != null; }

    public boolean isOverdue() {
        return !isReturned() && LocalDate.now().isAfter(dueDate);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public double getFineAmount() { return fineAmount; }
    public void setFineAmount(double fineAmount) { this.fineAmount = fineAmount; }

    public boolean isFinePaid() { return finePaid; }
    public void setFinePaid(boolean finePaid) { this.finePaid = finePaid; }
}
