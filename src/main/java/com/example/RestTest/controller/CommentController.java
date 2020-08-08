package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Comment;
import com.example.RestTest.domain.User;
import com.example.RestTest.repository.UserDataRepository;
import com.example.RestTest.services.CommentService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;
    private final UserDataRepository userDataRepository;

    @Autowired
    public CommentController(CommentService commentService, UserDataRepository userDataRepository) {
        this.commentService = commentService;
        this.userDataRepository = userDataRepository;
    }

    @PostMapping
    public Comment create(
            @RequestBody Comment comment,
            @AuthenticationPrincipal Principal user
    ){
        return commentService.create(user, comment);
    }
}
