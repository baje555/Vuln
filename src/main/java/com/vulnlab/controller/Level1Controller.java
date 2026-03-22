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
public class Level1Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/level1/login")
    public String level1Page() {
        return "level1";
    }

    @PostMapping("/level1/login")
    public String level1Login(@RequestParam String username, @RequestParam String password, Model model) {
        // VULNERABLE: SQL Injection - using string concatenation
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
        
        try {
            // Simulate SQL injection by checking if query contains OR
            if (username.contains("'") || username.contains("--") || username.contains("/*")) {
                // This is vulnerable - we're executing the injected query
                Optional<User> user = userRepository.findByUsername(username.replace("'", "").replace("--", "").replace("/*", ""));
                if (user.isPresent()) {
                    model.addAttribute("flag", "flag{sql_injection_success}");
                    return "level1";
                }
            }
            
            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent() && user.get().getPassword().equals(password)) {
                model.addAttribute("flag", "flag{sql_injection_success}");
                return "level1";
            }
        } catch (Exception e) {
            model.addAttribute("flag", "flag{sql_injection_success}");
            return "level1";
        }
        
        model.addAttribute("error", "Login failed");
        return "level1";
    }
}
