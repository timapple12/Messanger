package com.example.RestTest.domain;


import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@ToString(of={"id","text"})
@EqualsAndHashCode(of={"id"})
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
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

    @JsonView(Views.ID_NAME.class)
    private String link;

    @JsonView(Views.ID_NAME.class)
    private String title;

    @JsonView(Views.ID_NAME.class)
    private String description;

    @JsonView(Views.ID_NAME.class)
    private String cover;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.ID_NAME.class)
    private User user;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    @JsonView(Views.ID_NAME.class)
    private List<Comment> comments;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
