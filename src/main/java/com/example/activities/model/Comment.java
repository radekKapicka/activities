package com.example.activities.model;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DynamicInsert
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    private User user;

    @ManyToOne
    private Activity activity;

    @DateTimeFormat(pattern = "dd.MM.yyyy'T'HH:mm")
    private LocalDateTime time;

    private String commentText;

    public Comment() {
    }

    public Comment(User user, Activity activity, LocalDateTime time, String commentText) {
        this.user = user;
        this.activity = activity;
        this.time = time;
        this.commentText = commentText;
    }

    public Comment(int id, User user, Activity activity, LocalDateTime time, String commentText) {
        this.id = id;
        this.user = user;
        this.activity = activity;
        this.time = time;
        this.commentText = commentText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", user=" + user +
                ", activity=" + activity +
                ", time=" + time +
                ", commentText='" + commentText + '\'' +
                '}';
    }
}
