package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Text;
import com.example.RestTest.dto.EventType;
import com.example.RestTest.dto.ObjectType;
import com.example.RestTest.exceptions.NotFoundException;
import com.example.RestTest.repository.TextRepository;
import com.example.RestTest.util.WsSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("message")
public class MessageController {
    private final TextRepository messages;
    private LocalDateTime localDateTime;
    private final BiConsumer<EventType,Text> wsSender;
    @Autowired
    public MessageController(TextRepository messages, WsSender wsSender) {
        this.messages = messages;
        this.wsSender = wsSender.getSender(Views.ID_NAME.class, ObjectType.MESSAGE);
    }

    @GetMapping
    @JsonView(Views.ID_NAME.class)
    public List<Text> listOFMessages() {
        return messages.findAll();
    }
    @GetMapping("{id}")
    public Text getOneMessage(@PathVariable("id") Text id){
        return id;
    }
   /* private Map<String, String> getMessages(@PathVariable String id){
        return messages.stream()
                .filter(message->message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);                 // Throws 404 exception
    }*/
    @PostMapping
    public Text createMessage(@RequestBody Text message){

        message.setCreationTime(LocalDateTime.now());
        Text text = messages.save(message);
        wsSender.accept(EventType.CREATE,text);
        return text;
    }

    @PutMapping("{id}")                 //refresh' the list of messages
    public Text refresh(@PathVariable("id") Text textFromDb,
                        @RequestBody Text message){
        BeanUtils.copyProperties(message,textFromDb,"id");              // copy from messages to textFromDb ignoring id
        Text refreshedMessage=messages.save(textFromDb);
        wsSender.accept(EventType.UPDATE,refreshedMessage);
        return refreshedMessage;
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Text message){
        if(messages==null){
            throw new NotFoundException();
        }else{
            messages.delete(message);
            wsSender.accept(EventType.REMOVE,message);
        }
    }

}
