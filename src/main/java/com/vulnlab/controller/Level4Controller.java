package com.vulnlab.controller;

import com.vulnlab.entity.User;
import com.vulnlab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class Level4Controller {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/level4/user")
    public String level4User(@RequestParam(required = false) Long id, Model model) {
        // VULNERABLE: IDOR - no access control check
        if (id != null) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                model.addAttribute("flag", "flag{idor_access}");
                return "level4";
            }
        }
        model.addAttribute("error", "User not found");
        return "level4";
    }
}
