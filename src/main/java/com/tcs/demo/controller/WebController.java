package com.tcs.demo.controller;

import com.tcs.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {

    private final UserService service;

    public WebController(UserService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password) {

        service.registerUser(username, password);
        return "redirect:/login";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage() {
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String username,
                                @RequestParam String newPassword,
                                Model model) {
        try {
            service.resetPassword(username, newPassword);
            model.addAttribute("message", "Password reset successfully. Please login with your new password.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "reset-password";
        }
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}