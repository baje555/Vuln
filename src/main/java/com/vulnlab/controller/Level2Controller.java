package com.vulnlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Level2Controller {

    @GetMapping("/level2/search")
    public String level2Search(@RequestParam(required = false) String q, Model model) {
        // VULNERABLE: Reflected XSS - no sanitization
        if (q != null && !q.isEmpty()) {
            model.addAttribute("query", q);
            model.addAttribute("result", "Search results for: " + q);
            
            // Check if XSS payload is detected
            if (q.contains("<script>") || q.contains("alert(") || q.contains("onerror=")) {
                model.addAttribute("flag", "flag{xss_reflected}");
            }
        }
        return "level2";
    }
}
