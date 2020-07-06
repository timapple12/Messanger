package com.example.RestTest.controller;

import com.example.RestTest.domain.User;
import com.example.RestTest.domain.UserDetailsImpl;
import com.example.RestTest.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
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
                       Principal principal) {
        OAuth2Authentication auth = (OAuth2Authentication) principal;
       // System.out.println(auth.getUserAuthentication().getDetails());
        HashMap<Object, Object> data = new HashMap<>();
        try{
            data.put("profile", auth.getUserAuthentication().getDetails());
            data.put("messages", messageRepository.findAll());
        }catch (Exception e){
            System.err.println(e);
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode","dev".equals(mode));

        return "index.html";
    }

    @Override
    public String toString() {
        return "MainController{" +
                "mode='" + mode + '\'' +
                ", messageRepository=" + messageRepository +
                '}';
    }
}
