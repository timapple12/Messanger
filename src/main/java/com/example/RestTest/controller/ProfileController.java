package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.User;
import com.example.RestTest.domain.UserSubscription;
import com.example.RestTest.repository.UserDataRepository;
import com.example.RestTest.service.ProfileService;
import com.example.RestTest.service.UserAuthService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService){
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

    @GetMapping("get-subscribers/{id}")
    @JsonView(Views.ID_NAME.class)
    public List<UserSubscription> subscribers(@PathVariable("id") User channel){
        return profileService.getSubs(channel);
    }

    @PostMapping("change-status/{id}")
    @JsonView(Views.ID_NAME.class)
    public UserSubscription changeSubscriptionsStatus(@PathVariable("id") User sub,
                                                      Principal principal
    ){
        return profileService.changeSubStatus(sub, principal);
    }
}
