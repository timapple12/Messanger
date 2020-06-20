package com.example.RestTest.repository;

import com.example.RestTest.domain.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text,Integer> {

}
