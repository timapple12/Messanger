package com.example.RestTest.controller;

import com.example.RestTest.domain.User;
import com.example.RestTest.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {
    private final TextRepository messageRepository;

    @Autowired
    public MainController(TextRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @GetMapping
    public String main(@AuthenticationPrincipal User user,
                       Model model,
                       Map<String,Object>secondModel) {
        HashMap<Object, Object> data = new HashMap<>();

        data.put("profile", user);
        data.put("messages", messageRepository.findAll());
        model.addAttribute("frontendData", data);
        secondModel.put("test","someValue");
        return "index.html";
    }

}
