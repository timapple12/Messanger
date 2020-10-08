package com.example.RestTest.domain;

import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.annotation.*;
import org.springframework.security.core.Authentication;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table

public class User implements Serializable {
    @Id
    @JsonView(Views.ID_NAME.class)
    private String id;                  // id fills from the Google side, type str
    @JsonView(Views.ID_NAME.class)
    private String username;
    private String password;
    @JsonView(Views.Profile.class)
    private String userGender;
    @JsonView(Views.ID_NAME.class)
    private String email;
    @JsonView(Views.ID_NAME.class)
    private String userData;
    @JsonView(Views.Profile.class)
    private String location;

    @JsonView(Views.Profile.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSeenActivity;

    @JsonView(Views.Profile.class)
    @OneToMany(
            mappedBy = "subscriber",
            orphanRemoval = true
    )
    private Set<UserSubscription> subscriptions = new HashSet<>();

    @OneToMany(
            mappedBy = "channel",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    @JsonView(Views.Profile.class)
    private Set<UserSubscription> subscribers = new HashSet<>();


    public Set<UserSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<UserSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<UserSubscription> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<UserSubscription> subscribers) {
        this.subscribers = subscribers;
    }

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


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userGender='" + userGender + '\'' +
                ", email='" + email + '\'' +
                ", userData='" + userData + '\'' +
                ", location='" + location + '\'' +
                ", lastSeenActivity=" + lastSeenActivity +
                '}';
    }
}
