package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Text;
import com.example.RestTest.domain.User;
import com.example.RestTest.dto.PageDto;
import com.example.RestTest.repository.UserDataRepository;
import com.example.RestTest.service.MessageService;
import com.example.RestTest.service.UserAuthService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.security.Principal;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageService messageService;
    private final UserDataRepository userDataRepository;
    @Autowired
    public MessageController(MessageService messageService, UserDataRepository userDataRepository) {
        this.messageService = messageService;
        this.userDataRepository = userDataRepository;
    }

    @GetMapping
    @JsonView(Views.ID_NAME.class)
    public PageDto listOFMessages(@PageableDefault(size = 4, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable, Principal principal) {

        User authorisedUser = new UserAuthService(userDataRepository).getAuthorisedUser(principal);
        return messageService.findAll(pageable, authorisedUser);
    }

    @GetMapping("{id}")
    public Text getOneMessage(@PathVariable("id") Text id) {
        return id;
    }

    @PostMapping
    public Text createMessage(@RequestBody Text message, Principal principal) {
        return messageService.createMessage(message, principal);
    }

    @PutMapping("{id}")                 //refresh' the list of messages
    public Text refresh(@PathVariable("id") Text textFromDb,
                        @RequestBody Text message) {
       return messageService.refresh(textFromDb, message);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Text message) {
        messageService.deleteMessage(message);
    }


}
