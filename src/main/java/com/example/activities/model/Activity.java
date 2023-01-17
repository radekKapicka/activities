package com.example.activities.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private float time;
    private Date dateFrom;
    private Date dateTo;
    private float priority;
    private String state;

    private float timeWorked;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mother_activity_id")
    private Activity motherActivity;

    @OneToMany(mappedBy = "motherActivity")
    private List<Activity> childActivities = new ArrayList<>();

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "activity",
            cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "activity",
            cascade = CascadeType.ALL)
    private List<WorkRegister> workRegisters;

    public Activity getMotherActivity() {
        return motherActivity;
    }

    public void setMotherActivity(Activity motherActivity) {
        this.motherActivity = motherActivity;
    }


    public Activity(String name, float time, Date dateFrom, Date dateTo, float priority, String state, String description, User user, int timeWorked) {
        this.name = name;
        this.time = time;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.priority = priority;
        this.state = state;
        this.description = description;
        this.user = user;
        this.timeWorked = timeWorked;
    }

    public Activity(int id, String name, float time, Date dateFrom, Date dateTo, float priority, String state, float timeWorked, String description, User user) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.priority = priority;
        this.state = state;
        this.timeWorked = timeWorked;
        this.description = description;
        this.user = user;
    }

    public Activity(int id, String name, float time, Date dateFrom, Date dateTo, float priority, String state, float timeWorked, String description, User user, Activity motherActivity) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.priority = priority;
        this.state = state;
        this.timeWorked = timeWorked;
        this.description = description;
        this.user = user;
        this.motherActivity = motherActivity;
    }

    public Activity() {
    }

    public Activity(int id, String name, float time, Date dateFrom, Date dateTo, float priority, String state, float timeWorked, String description, User user, List<WorkRegister> workRegisters) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.priority = priority;
        this.state = state;
        this.timeWorked = timeWorked;
        this.description = description;
        this.user = user;
        this.workRegisters = workRegisters;
    }

    public Activity(int id, String name, float time, Date dateFrom, Date dateTo, float priority, String state, float timeWorked, String description, Activity motherActivity, List<Activity> childActivities, User user, List<WorkRegister> workRegisters) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.priority = priority;
        this.state = state;
        this.timeWorked = timeWorked;
        this.description = description;
        this.motherActivity = motherActivity;
        this.childActivities = childActivities;
        this.user = user;
        this.workRegisters = workRegisters;
    }

    public Activity(int id, String name, float time, Date dateFrom, Date dateTo, float priority, String state, float timeWorked, String description, Activity motherActivity, List<Activity> childActivities, User user, List<Comment> comments, List<WorkRegister> workRegisters) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.priority = priority;
        this.state = state;
        this.timeWorked = timeWorked;
        this.description = description;
        this.motherActivity = motherActivity;
        this.childActivities = childActivities;
        this.user = user;
        this.comments = comments;
        this.workRegisters = workRegisters;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Activity> getChildActivities() {
        return childActivities;
    }

    public void setChildActivities(List<Activity> childActivities) {
        this.childActivities = childActivities;
    }

    public List<WorkRegister> getWorkRegisters() {
        return workRegisters;
    }

    public void setWorkRegisters(List<WorkRegister> workRegisters) {
        this.workRegisters = workRegisters;
    }

    public float getTimeWorked() {
        return timeWorked;
    }

    public void setTimeWorked(float timeWorked) {
        this.timeWorked = timeWorked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getPriority() {
        return priority;
    }

    public void setPriority(float priority) {
        this.priority = priority;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", priority=" + priority +
                ", state='" + state + '\'' +
                ", timeWorked=" + timeWorked +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", workRegisters=" + workRegisters +
                '}';
    }
}
