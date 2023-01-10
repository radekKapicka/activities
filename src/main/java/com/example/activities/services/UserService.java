package com.example.activities.services;

import com.example.activities.model.Board;
import com.example.activities.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    void addUser(User p);


}