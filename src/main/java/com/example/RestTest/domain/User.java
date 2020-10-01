package com.example.RestTest.domain;

import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.Authentication;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table

public class User implements Serializable {
    @Id
    private String id;                  // id fills from the Google side, type str
    @JsonView(Views.ID_NAME.class)
    private String username;
    private String password;
    private String userGender;
    @JsonView(Views.ID_NAME.class)
    private String email;
    @JsonView(Views.ID_NAME.class)
    private String userData;
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSeenActivity;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getLastSeenActivity() {
        return lastSeenActivity;
    }

    public void setLastSeenActivity(LocalDateTime lastSeenActivity) {
        this.lastSeenActivity = lastSeenActivity;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
