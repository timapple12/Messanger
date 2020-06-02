package com.example.RestTest.controller;

import com.example.RestTest.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter=4;
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("id", "1");
            put("text", "someText1");
        }});
        add(new HashMap<String, String>() {{
            put("id", "2");
            put("text", "someText2");
        }});
        add(new HashMap<String, String>() {{
            put("id", "3");
            put("text", "someText3");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> listOFMessages() {
        return messages;
    }
    @GetMapping("{id}")
    public Map<String, String> getOneMessage(@PathVariable String id){
        return getMessages(id);
    }
    private Map<String, String> getMessages(@PathVariable String id){
        return messages.stream()
                .filter(message->message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);                 // Throws 404 exception
    }
    @PostMapping
    public Map<String, String> createMessage(@RequestBody Map<String, String> message){
        message.put("id",String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @PutMapping("{id}")                 //refresh' the list of messages
    public Map<String, String> refresh(@PathVariable String id,
                                       @RequestBody Map<String, String> message){
        Map<String, String> messageFromDb=getMessages(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id",id);
        return messageFromDb;
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String, String> message =getMessages(id);
        messages.remove(message);

    }
}
