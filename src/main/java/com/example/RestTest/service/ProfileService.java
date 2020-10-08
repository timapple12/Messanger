package com.example.RestTest.service;

import com.example.RestTest.domain.User;
import com.example.RestTest.domain.UserSubscription;
import com.example.RestTest.repository.UserDataRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final UserDataRepository userDataRepository;

    public ProfileService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public User changeSub(User user, Principal principal) {
        User authUser = new UserAuthService(userDataRepository).getAuthorisedUser(principal);
        List<UserSubscription> subs = user.getSubscribers()
                .stream()
                .filter(sub -> sub.getSubscriber().equals(authUser))
                .collect(Collectors.toList());

        if(subs.isEmpty()){
            UserSubscription subscription = new UserSubscription(user, authUser);
            user.getSubscribers().add(subscription);
        }else {
            user.getSubscribers().removeAll(subs);
        }

        return userDataRepository.save(user);
    }
}
