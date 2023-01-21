package com.example.activities.repositories;

import com.example.activities.model.Activity;
import com.example.activities.model.Comment;
import com.example.activities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("activityRepository")
public interface ActivityRepository extends JpaRepository<Activity,Integer> {

    @Query("SELECT a FROM Activity a ORDER BY a.dateTo DESC")
    List<Activity> findAllActivitiesDesc();
}
