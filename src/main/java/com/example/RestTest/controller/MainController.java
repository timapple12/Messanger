package com.example.RestTest.controller;

import com.example.RestTest.domain.User;
import com.example.RestTest.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {
    @Value("${spring.profiles.active}")
    private String mode;
    private final TextRepository messageRepository;

    @Autowired
    public MainController(TextRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public String main(Model model,
                       Map<String,Object>secondModel,
                       @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        data.put("profile", user);
        data.put("messages", messageRepository.findAll());
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode","dev".equals(mode));
        secondModel.put("test","someValue");
        return "index.html";
    }



}