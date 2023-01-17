package com.example.activities.services;

import com.example.activities.model.Activity;
import com.example.activities.model.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment c);

    List<Comment> getAll();

    Comment findComment(int id);
}
