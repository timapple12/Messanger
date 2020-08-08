package com.example.RestTest.services;

import com.example.RestTest.domain.Comment;
import com.example.RestTest.domain.User;
import com.example.RestTest.repository.CommentsRepository;
import com.example.RestTest.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import org.apache.logging.log4j.Logger;
@Service
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final UserDataRepository userDataRepository;

    @Autowired
    public CommentService(CommentsRepository commentsRepository, UserDataRepository userDataRepository) {
        this.commentsRepository = commentsRepository;
        this.userDataRepository = userDataRepository;
    }

    public Comment create(Principal users, Comment comment) {
        User authorisedUser = new UserFactoryService().getAuthorisedUser(users, userDataRepository);

        comment.setAuthor(authorisedUser);
        commentsRepository.save(comment);
        return comment;
    }
}
