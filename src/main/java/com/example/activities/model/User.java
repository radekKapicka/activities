package com.example.activities.model;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicInsert
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL)
    private List<Activity> activities;

    @OneToMany(mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<WorkRegister> workRegisters;

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User(int id, String username, String password, List<Activity> activities, List<WorkRegister> workRegisters, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.activities = activities;
        this.workRegisters = workRegisters;
        this.role = role;
    }

    public List<WorkRegister> getWorkRegisters() {
        return workRegisters;
    }

    public void setWorkRegisters(List<WorkRegister> workRegisters) {
        this.workRegisters = workRegisters;
    }

    public User(String username, String password, String user) {
        this.username = username;
        this.password = password;
        this.role = user;
    }

    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
