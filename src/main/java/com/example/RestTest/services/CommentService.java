package com.example.RestTest.services;

import com.example.RestTest.domain.Comment;
import com.example.RestTest.domain.Text;
import com.example.RestTest.domain.User;
import com.example.RestTest.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class CommentService {
    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public Comment create(User user, Comment comment) {
        // OAuth2Authentication auth = (OAuth2Authentication) principal;
       // comment.setAuthor(auth.getUserAuthentication().getName());
        comment.setAuth(user);
        commentsRepository.save(comment);
        return comment;
    }
}
