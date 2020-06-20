package com.example.RestTest.domain;


import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@ToString(of={"id","text"})
@EqualsAndHashCode(of={"id"})
@Data                     //if we add @Data annotation all getters&setters shall be created automatically, but it doesn't work :D
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.ID_NAME.class)
    private Integer id;

    @JsonView(Views.ID_NAME.class)
    private String text;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.Date.class)
    private LocalDateTime creationTime;

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
