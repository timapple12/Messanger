package com.example.RestTest.dto;

import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;

@JsonView(Views.ID_NAME.class)
public class WsDto {
    private ObjectType objectType;
    private EventType eventType;
    @JsonRawValue
    private String mess;

    public WsDto(ObjectType objectType, EventType eventType, String val) {
        this.eventType=eventType;
        this.mess=val;
        this.objectType=objectType;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
