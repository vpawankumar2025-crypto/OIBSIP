package com.library.controller;

import com.library.model.User;
import com.library.service.LibraryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Routes for logged-in library members (non-admin). Protected by
 * AuthInterceptor for the /user/** prefix.
 */
@Controller
@org.springframework.web.bind.annotation.RequestMapping("/user")
public class UserController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("loans", libraryService.getLoansForUser(currentUser));
        model.addAttribute("reservations", libraryService.getReservationsForUser(currentUser));
        model.addAttribute("books", libraryService.getAllBooks());
        return "user-dashboard";
    }

    @GetMapping("/issue/{bookId}")
    public String issue(@PathVariable Long bookId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        String error = libraryService.issueBook(bookId, currentUser);
        return redirectWithMessage(error);
    }

    @GetMapping("/return/{loanId}")
    public String returnBook(@PathVariable Long loanId, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        libraryService.returnBook(loanId, currentUser);
        return "redirect:/user/dashboard";
    }

    @GetMapping("/reserve/{bookId}")
    public String reserve(@PathVariable Long bookId, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        libraryService.reserveBook(bookId, currentUser);
        return "redirect:/user/dashboard";
    }

    private String redirectWithMessage(String error) {
        // Simple flash-free redirect; errors are non-fatal for this small app.
        return "redirect:/user/dashboard";
    }
}
