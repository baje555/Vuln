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
public class Level3Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/level3/profile")
    public String level3Profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Optional<User> dbUser = userRepository.findById(user.getId());
        if (dbUser.isPresent()) {
            model.addAttribute("user", dbUser.get());
        }
        return "level3";
    }

    @PostMapping("/level3/profile")
    public String level3UpdateProfile(@RequestParam String bio, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        // VULNERABLE: Stored XSS - no sanitization of bio
        Optional<User> dbUser = userRepository.findById(user.getId());
        if (dbUser.isPresent()) {
            User updatedUser = dbUser.get();
            updatedUser.setBio(bio);
            userRepository.save(updatedUser);
            session.setAttribute("user", updatedUser);
            
            // Check if XSS payload is detected
            if (bio.contains("<script>") || bio.contains("alert(") || bio.contains("onerror=")) {
                model.addAttribute("flag", "flag{stored_xss}");
            }
            
            model.addAttribute("user", updatedUser);
            model.addAttribute("success", "Profile updated");
        }
        return "level3";
    }
}
