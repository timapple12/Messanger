package com.example.RestTest.domain;


import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
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
@Data                     //if we add @Data annotation all getters&setters shall be created automatically, but it doesn't work :D
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.ID_NAME.class)
    private Integer id;

    @JsonView(Views.ID_NAME.class)
    private String text;

    @JsonView(Views.ID_NAME.class)
    private String link;

    @JsonView(Views.ID_NAME.class)
    private String title;

    @JsonView(Views.ID_NAME.class)
    private String cover;

    @JsonView(Views.ID_NAME.class)
    private String description;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.Date.class)
    private LocalDateTime creationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "text", orphanRemoval = true)
    private List<Comment> comments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

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

    @Override
    public String toString() {
        return "Text{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", cover='" + cover + '\'' +
                ", description='" + description + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
