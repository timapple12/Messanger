package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.example.RestTest.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
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
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        this.objectWriter = objectMapper.setConfig(objectMapper.getSerializationConfig())
                .writerWithView(Views.FullMessage.class);
    }

    @GetMapping
    public String main(Model model,
                       Principal principal) throws JsonProcessingException {
        OAuth2Authentication auth = (OAuth2Authentication) principal;

        HashMap<Object, Object> data = new HashMap<>();

        if (auth != null) {
            data.put("profile", auth.getUserAuthentication().getDetails());
            model.addAttribute("messages", objectWriter.writeValueAsString(messageRepository.findAll()));
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(mode));

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
