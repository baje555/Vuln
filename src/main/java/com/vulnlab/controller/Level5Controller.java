package com.vulnlab.controller;

import com.vulnlab.entity.User;
import com.vulnlab.repository.FileRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class Level5Controller {

    @Autowired
    private FileRepository fileRepository;

    @GetMapping("/level5/upload")
    public String level5Upload() {
        return "level5";
    }

    @PostMapping("/level5/upload")
    public String level5HandleUpload(@RequestParam MultipartFile file, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        try {
            // VULNERABLE: No file type validation
            String uploadDir = "uploads/";
            new File(uploadDir).mkdirs();
            
            String filename = file.getOriginalFilename();
            String filepath = uploadDir + filename;
            
            Files.write(Paths.get(filepath), file.getBytes());
            
            // Check if executable file was uploaded
            if (filename.endsWith(".jsp") || filename.endsWith(".exe") || filename.endsWith(".sh")) {
                model.addAttribute("flag", "flag{file_upload_rce}");
            }
            
            model.addAttribute("success", "File uploaded: " + filename);
        } catch (Exception e) {
            model.addAttribute("error", "Upload failed: " + e.getMessage());
        }
        
        return "level5";
    }
}
