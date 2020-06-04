package com.example.RestTest.repository;

import com.example.RestTest.domain.TextController;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<TextController,Integer> {

}
