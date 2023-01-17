package com.example.activities.repositories;

import com.example.activities.model.Activity;
import com.example.activities.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
