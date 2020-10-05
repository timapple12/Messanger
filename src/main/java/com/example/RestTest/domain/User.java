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

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "channel_id")
    )
    @JsonIdentityReference                                                     // To avoid stackOverflow error
    @JsonIdentityInfo(                                                         // If this field serialized more than 2 times it will be replaced with 'id'
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    @JsonView(Views.Profile.class)
    private Set<User> subscriptions = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscribers",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    @JsonIdentityReference                                                     // To avoid stackOverflow error
    @JsonIdentityInfo(                                                         // If this field serialized more than 2 times it will be replaced with 'id'
            property = "id",
            generator = ObjectIdGenerators.PropertyGenerator.class
    )
    @JsonView(Views.Profile.class)
    private Set<User> subscribers = new HashSet<>();

    public Set<User> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<User> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
