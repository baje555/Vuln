package com.vulnlab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller
public class Level6Controller {

    @GetMapping("/level6/ping")
    public String level6Ping() {
        return "level6";
    }

    @PostMapping("/level6/ping")
    public String level6HandlePing(@RequestParam String ip, Model model) {
        try {
            // VULNERABLE: Command Injection - no sanitization
            String command = "ping -c 1 " + ip;
            Process process = Runtime.getRuntime().exec(command);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            
            model.addAttribute("result", output.toString());
            
            // Check if command injection payload is detected
            if (ip.contains(";") || ip.contains("|") || ip.contains("&") || ip.contains("`")) {
                model.addAttribute("flag", "flag{command_injection}");
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ping failed: " + e.getMessage());
        }
        
        return "level6";
    }
}
