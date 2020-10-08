package com.example.RestTest.repository;

import com.example.RestTest.domain.Text;
import com.example.RestTest.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface TextRepository extends JpaRepository<Text,Integer> {
    @EntityGraph(attributePaths = {"comments"})
    Page<Text> findByUserIn(List<User> users, Pageable pageable);
}
