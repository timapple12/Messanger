package com.example.RestTest.service;

import com.example.RestTest.JsonViews.Views;
import com.example.RestTest.domain.Comment;
import com.example.RestTest.domain.Text;
import com.example.RestTest.domain.User;
import com.example.RestTest.dto.EventType;
import com.example.RestTest.dto.ObjectType;
import com.example.RestTest.repository.CommentRepository;
import com.example.RestTest.repository.UserDataRepository;
import com.example.RestTest.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepository commentsRepository;
    private final UserDataRepository userDataRepository;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserDataRepository userDataRepository, WsSender wsSender) {
        this.commentsRepository = commentRepository;
        this.userDataRepository = userDataRepository;
        this.wsSender = wsSender.getSender(Views.FullComment.class, ObjectType.COMMENT);
    }

    public Comment create(Principal users, Comment comment) {
        User authorisedUser = new UserGetAuthService().getAuthorisedUser(users, userDataRepository);

        comment.setUser(authorisedUser);
        Comment savedComment = commentsRepository.save(comment);
        wsSender.accept(EventType.CREATE, savedComment);
        return savedComment;
    }
}