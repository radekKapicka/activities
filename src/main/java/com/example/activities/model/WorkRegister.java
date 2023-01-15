package com.example.activities.model;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
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

    private Date timeFrom;

    private Date timeTo;



    public WorkRegister(int id, User user, Activity activity, Date timeFrom, Date timeTo) {
        this.id = id;
        this.user = user;
        this.activity = activity;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public WorkRegister(User user, Activity activity, Date timeFrom, Date timeTo) {
        this.user = user;
        this.activity = activity;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
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

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Date timeTo) {
        this.timeTo = timeTo;
    }
}
