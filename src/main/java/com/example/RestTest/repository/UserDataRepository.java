package com.example.RestTest.repository;

import com.example.RestTest.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<User,String> {
    @EntityGraph(attributePaths = {"subscriptions", "subscribers"})
    Optional<User> findById(String s);
}
