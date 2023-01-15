package com.example.activities.model;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DynamicInsert
public class WorkRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Activity activity;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timeFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime timeTo;

    private float partOfTime;



    public WorkRegister(int id, User user, Activity activity, LocalDateTime timeFrom, LocalDateTime timeTo) {
        this.id = id;
        this.user = user;
        this.activity = activity;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public WorkRegister(User user, Activity activity, LocalDateTime timeFrom, LocalDateTime timeTo) {
        this.user = user;
        this.activity = activity;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public WorkRegister(User user, Activity activity, LocalDateTime timeFrom, LocalDateTime timeTo, float partOfTime) {
        this.user = user;
        this.activity = activity;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.partOfTime = partOfTime;
    }

    public WorkRegister(int id, User user, Activity activity, LocalDateTime timeFrom, LocalDateTime timeTo, float partOfTime) {
        this.id = id;
        this.user = user;
        this.activity = activity;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.partOfTime = partOfTime;
    }

    public float getPartOfTime() {
        return partOfTime;
    }

    public void setPartOfTime(float partOfTime) {
        this.partOfTime = partOfTime;
    }

    public WorkRegister() {
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

    public LocalDateTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalDateTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalDateTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalDateTime timeTo) {
        this.timeTo = timeTo;
    }


}
