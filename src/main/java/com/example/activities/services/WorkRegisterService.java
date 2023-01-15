package com.example.activities.services;

import com.example.activities.model.Activity;
import com.example.activities.model.WorkRegister;

import java.util.List;

public interface WorkRegisterService {

    void addRegister(WorkRegister w);

    List<WorkRegister> getAll();

    WorkRegister findRegister(int id);
}
