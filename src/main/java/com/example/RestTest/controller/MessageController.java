package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Text;
import com.example.RestTest.dto.PageDto;
import com.example.RestTest.service.MessageService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    @JsonView(Views.ID_NAME.class)
    public PageDto listOFMessages(@PageableDefault(size = 4, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable) {
        PageDto pageDto = messageService.findAll(pageable);
        System.out.println(pageDto);
        System.out.println(pageable.toString());
        return pageDto;
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
