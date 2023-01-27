package com.example.activities.services;

import com.example.activities.model.Activity;
import com.example.activities.model.Comment;
import com.example.activities.model.User;

import java.util.List;

public interface CommentService {

    void addComment(Comment c);

    List<Comment> getAll();

    Comment findComment(int id);

    void deleteComment(Comment c);
}
