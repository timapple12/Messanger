package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.User;
import com.example.RestTest.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{id}")
    @JsonView(Views.Profile.class)
    public User getUser(@PathVariable("id") User user){
        return user;
    }

    @PostMapping("change-subscription/{id}")
    @JsonView(Views.Profile.class)
    public User changeSubscription(@PathVariable("id") User user,
            Principal principal){

        return profileService.changeSub(user, principal);
    }
}
