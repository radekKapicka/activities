package com.example.activities.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class Activities {

    private List<Activity> activities;

    @XmlElement
    public List<Activity> getActivityList() {
        if (activities == null) {
            activities = new ArrayList<>();
        }
        return activities;
    }
}
