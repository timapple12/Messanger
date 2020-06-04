package com.example.RestTest.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table
@EqualsAndHashCode(of={"id"})
@ToString(of={"id","message"})
@Data                     //if we add @Data annotation all getters&setters shall be created automatically, but it doesn't work :D
public class TextController {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
