package com.library.controller;

import com.library.model.User;
import com.library.service.LibraryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("books", libraryService.getAllBooks());
        return "index";
    }

    /** Public/browse catalogue with optional search or category filter. */
    @GetMapping("/books")
    public String browseBooks(@RequestParam(required = false) String q,
                               @RequestParam(required = false) String category,
                               HttpSession session, Model model) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        if (q != null && !q.isBlank()) {
            model.addAttribute("books", libraryService.search(q));
            model.addAttribute("query", q);
        } else if (category != null && !category.isBlank()) {
            model.addAttribute("books", libraryService.browseByCategory(category));
            model.addAttribute("query", category);
        } else {
            model.addAttribute("books", libraryService.getAllBooks());
        }
        return "books";
    }

    @GetMapping("/contact")
    public String contactPage(HttpSession session, Model model) {
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        return "contact";
    }

    @PostMapping("/contact")
    public String submitContact(@RequestParam String name, @RequestParam String email,
                                 @RequestParam String message, Model model, HttpSession session) {
        libraryService.submitContactMessage(name, email, message);
        model.addAttribute("currentUser", session.getAttribute("currentUser"));
        model.addAttribute("success", "Thanks! Your message has been sent to the library team.");
        return "contact";
    }
}
