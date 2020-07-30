package com.example.RestTest.repository;

import com.example.RestTest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<User, String> {

}
