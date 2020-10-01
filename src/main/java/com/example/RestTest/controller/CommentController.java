package com.example.RestTest.controller;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Comment;
import com.example.RestTest.domain.Text;
import com.example.RestTest.dto.EventType;
import com.example.RestTest.dto.ObjectType;
import com.example.RestTest.service.CommentService;
import com.example.RestTest.util.WsSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;

    }


    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment createComment(@RequestBody Comment comment,
                                 Principal principal) {
        return commentService.create(principal, comment);
    }
}
