package com.example.todoapp.controller;

import com.example.todoapp.model.User;
import com.example.todoapp.service.UserService;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

 @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.findByUsernameOrNull(user.getUsername()) != null) {
            model.addAttribute("error", "Username already taken.");
            return "signup";
        }
        userService.saveUser(user);
        System.out.println("User registered, redirecting to login...");
        return "redirect:/login";
    }

 @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/profile/change-password")
public String changePassword(@RequestParam String oldPassword,
                             @RequestParam String newPassword,
                             Principal principal,
                             RedirectAttributes redirectAttributes) {
    try {
        userService.changePassword(principal.getName(), oldPassword, newPassword);
        redirectAttributes.addFlashAttribute("success", "Password updated successfully!");
    } catch (IllegalArgumentException e) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());
    }
    return "redirect:/profile";
}

}
