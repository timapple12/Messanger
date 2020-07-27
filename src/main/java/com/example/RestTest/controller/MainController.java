package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.example.RestTest.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    @Value("${spring.profiles.active}")
    private String mode;
    private final TextRepository messageRepository;
    private final ObjectWriter objectWriter;

    @Autowired
    public MainController(TextRepository messageRepository, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.objectWriter = objectMapper.setConfig(objectMapper.getSerializationConfig())
                .writerWithView(Views.ID_NAME.class);
    }

    @GetMapping
    public String main(Model model,
                       Principal principal) {
        OAuth2Authentication auth = (OAuth2Authentication) principal;
       // System.out.println(auth.getUserAuthentication().getDetails());
        HashMap<Object, Object> data = new HashMap<>();
        try{
            data.put("profile", auth.getUserAuthentication().getDetails());
            model.addAttribute("messages", objectWriter.writeValueAsString(messageRepository.findAll()));
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
