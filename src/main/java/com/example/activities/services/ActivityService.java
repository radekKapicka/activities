package com.example.activities.services;

import com.example.activities.model.Activity;


import java.util.List;

public interface ActivityService {

    void addActivity(Activity a);

    List<Activity> getAll();

    Activity findActivity(int id);

    Activity updateActivity (Activity activity);

}
