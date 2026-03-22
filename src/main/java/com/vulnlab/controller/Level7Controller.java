package com.vulnlab.controller;

import com.vulnlab.entity.User;
import com.vulnlab.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class Level7Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/level7/admin")
    public String level7Admin(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        
        // VULNERABLE: Broken Authentication - no proper role check
        if (user != null) {
            // Just checking if user exists, not if they're admin
            Optional<User> dbUser = userRepository.findById(user.getId());
            if (dbUser.isPresent()) {
                model.addAttribute("user", dbUser.get());
                model.addAttribute("flag", "flag{admin_access}");
                return "level7";
            }
        }
        
        model.addAttribute("error", "Access denied");
        return "level7";
    }
}
