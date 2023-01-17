package com.example.activities.services;

import com.example.activities.model.Activity;
import com.example.activities.model.Comment;
import com.example.activities.repositories.ActivityRepository;
import com.example.activities.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("commentService")
@Component
public class MemCommentService implements CommentService{

    @Autowired
    private CommentRepository commentrepo;

    List<Comment> comments = new ArrayList<>();

    @Override
    public void addComment(Comment c) {
        commentrepo.save(c);
    }

    @Override
    public List<Comment> getAll() {
        return Collections.unmodifiableList(comments);
    }

    @Override
    public Comment findComment(int id) {
        return commentrepo.getOne(id);
    }
}
