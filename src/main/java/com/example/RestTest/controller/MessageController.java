package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.TextController;
import com.example.RestTest.repository.TextRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {
    private final TextRepository messages;
    private LocalDateTime localDateTime;
    @Autowired
    public MessageController(TextRepository messages) {
        this.messages = messages;
    }

    @GetMapping
    @JsonView(Views.ID_NAME.class)
    public List<TextController> listOFMessages() {
        return messages.findAll();
    }
    @GetMapping("{id}")
    public TextController getOneMessage(@PathVariable("id") TextController id){
        return id;
    }
   /* private Map<String, String> getMessages(@PathVariable String id){
        return messages.stream()
                .filter(message->message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);                 // Throws 404 exception
    }*/
    @PostMapping
    public TextController createMessage(@RequestBody TextController message){

        message.setCreationTime(LocalDateTime.now());
        return messages.save(message);
    }

    @PutMapping("{id}")                 //refresh' the list of messages
    public TextController refresh(@PathVariable("id") TextController textFromDb,
                                  @RequestBody TextController message){
        BeanUtils.copyProperties(message,textFromDb,"id");              // copy from messages to textFromDb ignoring id
        return messages.save(textFromDb);
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") TextController message){
       messages.delete(message);
    }
}
