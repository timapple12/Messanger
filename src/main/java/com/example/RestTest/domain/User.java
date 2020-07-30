package com.example.RestTest.domain;

import com.example.RestTest.JsonViews.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
public class User implements Serializable {
    @Id
    @JsonView(Views.ID_NAME.class)
    private String id;           // id fills from the Google side, type str


    @JsonView(Views.ID_NAME.class)
    private String username;
    private String password;
    private String userGender;
    private String email;
    @JsonView(Views.ID_NAME.class)
    private String userData;
    private String location;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastSeenActivity;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(userGender, user.userGender) &&
                Objects.equals(email, user.email) &&
                Objects.equals(userData, user.userData) &&
                Objects.equals(location, user.location) &&
                Objects.equals(lastSeenActivity, user.lastSeenActivity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, userGender, email, userData, location, lastSeenActivity);
    }
}