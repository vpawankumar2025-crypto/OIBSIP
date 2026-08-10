package com.library.controller;

import com.library.model.User;
import com.library.service.LibraryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                         HttpSession session, Model model) {
        Optional<User> user = libraryService.authenticate(username, password);
        if (user.isEmpty()) {
            model.addAttribute("error", "Invalid username or password.");
            return "login";
        }
        session.setAttribute("currentUser", user.get());
        return user.get().isAdmin() ? "redirect:/admin/dashboard" : "redirect:/user/dashboard";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,
                            @RequestParam String fullName, @RequestParam String email, Model model) {
        boolean created = libraryService.register(username, password, fullName, email);
        if (!created) {
            model.addAttribute("error", "That username is already taken.");
            return "register";
        }
        model.addAttribute("success", "Registration successful! You can now log in.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
