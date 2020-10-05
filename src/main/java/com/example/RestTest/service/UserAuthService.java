package com.example.RestTest.service;


import com.example.RestTest.domain.User;
import com.example.RestTest.repository.UserDataRepository;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class UserAuthService {
    private final UserDataRepository userDataRepository;

    public UserAuthService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public User getAuthorisedUser(Principal principal){
        OAuth2Authentication auth = (OAuth2Authentication) principal;
        Optional<User> user;
        LinkedHashMap hash;
        if(auth!=null){
            hash = (LinkedHashMap) auth.getUserAuthentication().getDetails();
            if(hash.containsKey("sub")){
                user = userDataRepository.findById((String) hash.get("sub"));
            }else {
                throw new IllegalArgumentException();
            }
            return user.get();
        }
       return null;
    }
}
