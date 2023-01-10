package com.example.activities.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private float time;
    @ManyToOne
    private User user;
    @ManyToOne
    private Board boardAct;
    private Date dateFrom;
    private Date dateTo;
    private int priority;
    private int state;

    public Activity(String name, float time, User user, Board boardAct, Date dateFrom, Date dateTo, int priority, int state) {
        this.name = name;
        this.time = time;
        this.user = user;
        this.boardAct = boardAct;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.priority = priority;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Board getBoardAct() {
        return boardAct;
    }

    public void setBoardAct(Board boardAct) {
        this.boardAct = boardAct;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
