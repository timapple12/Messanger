package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.User;
import com.example.RestTest.dto.PageDto;
import com.example.RestTest.repository.UserDataRepository;
import com.example.RestTest.service.MessageService;
import com.example.RestTest.service.UserAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    private final static int PAGE_SIZE = 4;
    @Value("${spring.profiles.active}")
    private String mode;
    private final MessageService messageService;
    private final UserDataRepository userDataRepository;

    private final ObjectWriter objectWriter;
    private final ObjectWriter profileWriter;


    public MainController(MessageService messageService, UserDataRepository userDataRepository, ObjectMapper objectMapper) {
        ObjectMapper mapper = objectMapper.setConfig(objectMapper.getSerializationConfig());

        this.messageService = messageService;
        this.userDataRepository = userDataRepository;

        this.objectWriter = mapper
                .writerWithView(Views.ID_NAME.class);

        this.profileWriter = mapper
                .writerWithView(Views.Profile.class);

    }


    @GetMapping
    public String main(Model model,
                       Principal principal) throws JsonProcessingException {
        HashMap<Object, Object> data = new HashMap<>();
        User user = new UserAuthService(userDataRepository).getAuthorisedUser(principal);
        if (user != null) {

            User userFromDb = userDataRepository.findById(user.getId()).get();

            model.addAttribute("profile", profileWriter.writeValueAsString(userFromDb));

            Sort orderById = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE, orderById);

            PageDto texts = messageService.findAll(pageRequest);
            String messages = objectWriter.writeValueAsString(texts.getMessages());

            model.addAttribute("messages", messages);
            data.put("currentPage", texts.getCurrentPage());
            data.put("totalPages", texts.getTotalPages());
        } else {
            model.addAttribute("profile", "null");
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(mode));

        return "index.html";
    }


}
