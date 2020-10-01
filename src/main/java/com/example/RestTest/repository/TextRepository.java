package com.example.RestTest.repository;

import com.example.RestTest.domain.Text;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TextRepository extends JpaRepository<Text,Integer> {
    @EntityGraph(attributePaths = {"comments"})
    List<Text> findAll();
}
