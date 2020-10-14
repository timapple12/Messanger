package com.example.RestTest.service;

import com.example.RestTest.domain.User;
import com.example.RestTest.domain.UserSubscription;
import com.example.RestTest.repository.UserDataRepository;
import com.example.RestTest.repository.UserSubRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final UserDataRepository userDataRepository;
    private final UserSubRepository userSubRepository;

    public ProfileService(UserDataRepository userDataRepository, UserSubRepository userSubRepository) {
        this.userDataRepository = userDataRepository;
        this.userSubRepository = userSubRepository;
    }

    public User changeSub(User user, Principal principal) {
        User authUser = new UserAuthService(userDataRepository).getAuthorisedUser(principal);
        List<UserSubscription> subs = user.getSubscribers()
                .stream()
                .filter(sub -> sub.getSubscriber().equals(authUser))
                .collect(Collectors.toList());

        if (subs.isEmpty()) {
            UserSubscription subscription = new UserSubscription(user, authUser);
            user.getSubscribers().add(subscription);
        } else {
            user.getSubscribers().removeAll(subs);
        }

        return userDataRepository.save(user);
    }

    public List<UserSubscription> getSubs(User channel) {
        return userSubRepository.findByChannel(channel);
    }

    public UserSubscription changeSubStatus(User sub, Principal principal) {
        User channel = new UserAuthService(userDataRepository).getAuthorisedUser(principal);

        UserSubscription subscription = userSubRepository.findByChannelAndSubscriber(channel, sub);
        subscription.setActive(!subscription.isActive());

        return userSubRepository.save(subscription);
    }
}
