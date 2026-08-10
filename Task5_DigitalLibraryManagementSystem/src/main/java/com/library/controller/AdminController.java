package com.library.controller;

import com.library.model.Book;
import com.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Admin-only routes: manage the book catalogue, view issued books, manage
 * members, mark fines as paid, and review contact messages. Protected by
 * AuthInterceptor for the /admin/** prefix.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("books", libraryService.getAllBooks());
        model.addAttribute("issuedLoans", libraryService.getAllIssuedLoans());
        model.addAttribute("members", libraryService.getAllMembers());
        model.addAttribute("unpaidFines", libraryService.getUnpaidFines());
        model.addAttribute("reservations", libraryService.getAllActiveReservations());
        model.addAttribute("contactMessages", libraryService.getAllContactMessages());
        return "admin-dashboard";
    }

    // ---- Book CRUD ----

    @GetMapping("/books/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "admin-book-form";
    }

    @PostMapping("/books/new")
    public String createBook(@RequestParam String title, @RequestParam String author,
                              @RequestParam String isbn, @RequestParam String category,
                              @RequestParam int quantity) {
        libraryService.addBook(title, author, isbn, category, quantity);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/books/{id}/edit")
    public String editBookForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", libraryService.getBook(id));
        return "admin-book-form";
    }

    @PostMapping("/books/{id}/edit")
    public String updateBook(@PathVariable Long id, @RequestParam String title, @RequestParam String author,
                              @RequestParam String isbn, @RequestParam String category,
                              @RequestParam int quantity) {
        libraryService.updateBook(id, title, author, isbn, category, quantity);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        libraryService.deleteBook(id);
        return "redirect:/admin/dashboard";
    }

    // ---- Fines ----

    @GetMapping("/fines/{loanId}/pay")
    public String payFine(@PathVariable Long loanId) {
        libraryService.markFinePaid(loanId);
        return "redirect:/admin/dashboard";
    }

    // ---- Contact messages ----

    @GetMapping("/messages/{id}/resolve")
    public String resolveMessage(@PathVariable Long id) {
        libraryService.resolveContactMessage(id);
        return "redirect:/admin/dashboard";
    }
}
