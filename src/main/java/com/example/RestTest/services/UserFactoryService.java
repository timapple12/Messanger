package com.example.RestTest.services;


import com.example.RestTest.domain.User;
import com.example.RestTest.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Optional;

@Service
public class UserFactoryService {

    public User getAuthorisedUser(Principal principal, UserDataRepository userDataRepository){
        OAuth2Authentication auth = (OAuth2Authentication) principal;
        Optional<User> user;
        LinkedHashMap hash;
        hash = (LinkedHashMap) auth.getUserAuthentication().getDetails();
        if(hash.containsKey("sub")){
            user = userDataRepository.findById((String) hash.get("sub"));
        }else {
            throw new IllegalArgumentException();
        }
        return user.get();
    }
}
