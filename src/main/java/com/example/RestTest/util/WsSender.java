package com.example.RestTest.util;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Text;
import com.example.RestTest.dto.EventType;
import com.example.RestTest.dto.ObjectType;
import com.example.RestTest.dto.WsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Component
public class WsSender {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper mapper;

    public WsSender(SimpMessagingTemplate simpMessagingTemplate, ObjectMapper mapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.mapper = mapper;
    }
    public <T> BiConsumer<EventType,T> getSender(Class view, ObjectType objectType){
        ObjectWriter objectWriter = mapper.setConfig(mapper.getSerializationConfig())
                .writerWithView(view);
        return (EventType eventType, T payload)->{
            String val=null;

            try {
                val=objectWriter.writeValueAsString(payload);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            simpMessagingTemplate.convertAndSend("/topic/activity",new WsDto(objectType,eventType,val));
        };
    }
}
