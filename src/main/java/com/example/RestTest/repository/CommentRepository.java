package com.example.RestTest.repository;

import com.example.RestTest.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
