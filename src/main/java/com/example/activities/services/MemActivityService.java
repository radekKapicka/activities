package com.example.activities.services;

import com.example.activities.model.Activity;
import com.example.activities.repositories.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("activityService")
@Component
public class MemActivityService implements ActivityService{

    @Autowired
    private ActivityRepository actrepo;

    List<Activity> activities = new ArrayList<>();

    @Override
    @Transactional
    public void addActivity(Activity a) {
        actrepo.save(a);
    }

    @Override
    public List<Activity> getAll() {
        return Collections.unmodifiableList(activities);
    }
}
