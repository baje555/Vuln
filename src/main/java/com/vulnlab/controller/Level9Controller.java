package com.vulnlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Level9Controller {

    @GetMapping("/level9/welcome")
    public String level9Welcome(@RequestParam(required = false) String name, Model model) {
        // VULNERABLE: SSTI - Server-Side Template Injection
        if (name != null && !name.isEmpty()) {
            // Simulate SSTI by checking for template expressions
            if (name.contains("${") || name.contains("#{") || name.contains("*{")) {
                model.addAttribute("flag", "flag{ssti_rce}");
            }
            
            model.addAttribute("message", "Welcome, " + name + "!");
        }
        return "level9";
    }
}
