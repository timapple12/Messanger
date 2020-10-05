package com.example.RestTest.service;

import com.example.RestTest.domain.User;
import com.example.RestTest.repository.UserDataRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Set;

@Service
public class ProfileService {
    private final UserDataRepository userDataRepository;

    public ProfileService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public User changeSub(User user, Principal principal) {
        User authUser = new UserAuthService(userDataRepository).getAuthorisedUser(principal);
        Set<User> subscribers = user.getSubscribers();

        if(subscribers.contains(authUser)){
            subscribers.remove(authUser);
        }else {
            subscribers.add(authUser);
        }

        return userDataRepository.save(user);
    }
}
