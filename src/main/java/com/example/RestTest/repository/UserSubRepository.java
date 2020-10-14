package com.example.RestTest.repository;

import com.example.RestTest.domain.User;
import com.example.RestTest.domain.UserSubscription;
import com.example.RestTest.domain.UserSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);

    List<UserSubscription> findByChannel(User channel);

    UserSubscription findByChannelAndSubscriber(User channel, User sub);
}
