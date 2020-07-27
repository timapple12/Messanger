package com.example.RestTest.domain;

import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.ID_NAME.class)
    private Long id;

    @JsonView(Views.ID_NAME.class)
    private String title;

    @ManyToOne
    @JoinColumn(name = "text_id")
    private Text text;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return Objects.equals(id, comment1.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

