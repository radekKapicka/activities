package com.example.activities.repositories;

import com.example.activities.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("activityRepository")
public interface ActivityRepository extends JpaRepository<Activity,Integer> {
}
