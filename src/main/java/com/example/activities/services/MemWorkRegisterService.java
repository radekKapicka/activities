package com.example.activities.services;

import com.example.activities.model.User;
import com.example.activities.model.WorkRegister;
import com.example.activities.repositories.UserRepository;
import com.example.activities.repositories.WorkRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("workRegisterService")
@Component
public class MemWorkRegisterService implements WorkRegisterService{

    @Autowired
    private WorkRegisterRepository registerrepo;

    List<WorkRegister> registers = new ArrayList<>();

    @Override
    public void addRegister(WorkRegister w) {
        registerrepo.save(w);
    }

    @Override
    public List<WorkRegister> getAll() {
        return Collections.unmodifiableList(registers);
    }

    @Override
    public WorkRegister findRegister(int id) {
        return registerrepo.getOne(id);
    }

    @Override
    public void deleteRegister(WorkRegister w) {
        registerrepo.delete(w);
    }
}
