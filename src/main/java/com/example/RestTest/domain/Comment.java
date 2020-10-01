package com.example.RestTest.domain;
import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Comment{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.ID_NAME.class)
    private Integer id;

    @JsonView(Views.ID_NAME.class)
    private String text;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "message_id")
    @JsonView(Views.FullComment.class)
    //@JsonIgnore
    private Text message;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    @JsonView(Views.ID_NAME.class)
    private User user;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Text getMessage() {
        return message;
    }

    public void setMessage(Text message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", message=" + message +
                ", user=" + user +
                '}';
    }
}