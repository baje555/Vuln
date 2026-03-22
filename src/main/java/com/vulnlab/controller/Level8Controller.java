package com.vulnlab.controller;

import com.vulnlab.entity.User;
import com.vulnlab.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class Level8Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/level8/password")
    public String level8Password(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "level8";
    }

    @PostMapping("/level8/password")
    public String level8ChangePassword(@RequestParam String newPassword, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // VULNERABLE: CSRF - no token validation
        Optional<User> dbUser = userRepository.findById(user.getId());
        if (dbUser.isPresent()) {
            User updatedUser = dbUser.get();
            updatedUser.setPassword(newPassword);
            userRepository.save(updatedUser);
            session.setAttribute("user", updatedUser);
            
            model.addAttribute("flag", "flag{csrf_success}");
            model.addAttribute("success", "Password changed successfully");
        }
        
        return "level8";
    }
}
