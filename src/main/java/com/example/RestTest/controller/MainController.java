package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Text;
import com.example.RestTest.domain.User;
import com.example.RestTest.repository.TextRepository;
import com.example.RestTest.repository.UserDataRepository;
import com.example.RestTest.service.UserGetAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {
    @Value("${spring.profiles.active}")
    private String mode;
    private final TextRepository messageRepository;
    private final UserDataRepository userDataRepository;
    private final ObjectWriter objectWriter;

    @Autowired
    public MainController(TextRepository messageRepository, UserDataRepository userDataRepository, ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.userDataRepository = userDataRepository;
        //objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        this.objectWriter = objectMapper.setConfig(objectMapper.getSerializationConfig())
                .writerWithView(Views.ID_NAME.class);
    }


    @GetMapping
    public String main(Model model,
                       Principal principal) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        User user = new UserGetAuthService().getAuthorisedUser(principal, userDataRepository);
        if (user != null) {
            data.put("profile", user);

            List<Text> texts = messageRepository.findAll();
            texts.forEach(obj -> MessageController.fillMetaData(obj));

            String messages = objectWriter.writeValueAsString(texts);
            model.addAttribute("messages", messages);
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(mode));

        return "index.html";
    }


}
