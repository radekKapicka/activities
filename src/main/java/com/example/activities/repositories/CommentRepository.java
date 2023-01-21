package com.example.activities.repositories;

import com.example.activities.model.Activity;
import com.example.activities.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    @Query("SELECT c FROM Comment c ORDER BY c.time DESC")
    List<Comment> findAllCommentsDesc();
}
