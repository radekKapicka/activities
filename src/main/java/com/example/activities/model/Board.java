package com.example.activities.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="board")
    private User user;

    @OneToMany(mappedBy="boardAct")
    private List<Activity> activities;

    public Board(User user) {
        this.user = user;
    }

    public Board() {
        this.id=id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", user=" + user +
                ", activities=" + activities +
                '}';
    }
}
