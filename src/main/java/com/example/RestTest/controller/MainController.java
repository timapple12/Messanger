package com.example.RestTest.controller;

import com.example.RestTest.domain.User;
import com.example.RestTest.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @PreAuthorize("hasRole('USER')")
    public String main(Model model,
                       Map<String,Object>secondModel,
                       @AuthenticationPrincipal User user) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HashMap<Object, Object> data = new HashMap<>();
        data.put("profile", user);
        data.put("messages", messageRepository.findAll());
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode","dev".equals(mode));
        secondModel.put("test","someValue");
        try{
            System.out.println(principal.toString());
        }catch (Exception e){

        }

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
