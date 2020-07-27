package com.example.RestTest.services;

import com.example.RestTest.domain.Comment;
import com.example.RestTest.domain.User;
import com.example.RestTest.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public Comment create(User user, Comment comment) {
        comment.setAuthor(user);
        commentsRepository.save(comment);
        return comment;
    }
}
