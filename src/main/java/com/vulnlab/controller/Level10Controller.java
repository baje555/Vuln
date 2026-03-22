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
public class Level10Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/level10/final")
    public String level10Final(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        return "level10";
    }

    @PostMapping("/level10/final")
    public String level10Challenge(@RequestParam String username, @RequestParam String action, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        // VULNERABLE: Chaining multiple vulnerabilities
        // 1. SQL Injection in username parameter
        // 2. Broken authentication (no role check)
        // 3. IDOR (accessing other users)
        
        try {
            Optional<User> targetUser = userRepository.findByUsername(username);
            
            if (targetUser.isPresent() && "promote".equals(action)) {
                User target = targetUser.get();
                target.setRole("admin");
                userRepository.save(target);
                
                model.addAttribute("flag", "flag{final_root}");
                model.addAttribute("success", "User promoted to admin");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Operation failed");
        }
        
        return "level10";
    }
}
