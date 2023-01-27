package com.example.activities.services;

import com.example.activities.model.Activity;
import com.example.activities.model.User;
import com.example.activities.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("userService")
@Component
public class MemUserService implements UserService{
    @Autowired
    private UserRepository userrepo;

    List<User> users = new ArrayList<>();

    @Override
    public List<User> getAll() {
        return Collections.unmodifiableList(users);
    }

    @Override
    @Transactional
        public void addUser(User u) {
            userrepo.save(u);
        }

    @Override
    public User findUser(int id) {
        return userrepo.getOne(id);
    }

    @Override
    public User updateUser(User user) {
        return userrepo.save(user);
    }

    @Override
    public void deleteUser(User u) {
        userrepo.delete(u);
    }

}
