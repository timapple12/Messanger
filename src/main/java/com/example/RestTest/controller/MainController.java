package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Text;
import com.example.RestTest.domain.User;
import com.example.RestTest.dto.PageDto;
import com.example.RestTest.repository.TextRepository;
import com.example.RestTest.repository.UserDataRepository;
import com.example.RestTest.service.MessageService;
import com.example.RestTest.service.UserGetAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.data.domain.Pageable;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final static int PAGE_SIZE = 4;
    @Value("${spring.profiles.active}")
    private String mode;
    private final MessageService messageService;
    private final UserDataRepository userDataRepository;
    private final ObjectWriter objectWriter;


    public MainController(MessageService messageService, UserDataRepository userDataRepository, ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.userDataRepository = userDataRepository;
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
            Sort orderById = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, PAGE_SIZE, orderById);
            PageDto texts = messageService.findAll(pageRequest);
            String messages = objectWriter.writeValueAsString(texts.getMessages());

            model.addAttribute("messages", messages);
            data.put("currentPage", texts.getCurrentPage());
            data.put("totalPages", texts.getTotalPages());
        }

        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(mode));

        return "index.html";
    }


}
