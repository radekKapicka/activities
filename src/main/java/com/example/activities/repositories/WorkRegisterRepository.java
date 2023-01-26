package com.example.activities.repositories;

import com.example.activities.model.Activity;
import com.example.activities.model.Comment;
import com.example.activities.model.WorkRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Repository("workRegisterRepository")
public interface WorkRegisterRepository extends JpaRepository<WorkRegister,Integer> {

    @Query("SELECT w FROM WorkRegister w ORDER BY w.timeTo DESC")
    List<WorkRegister> findAllReportsDesc();
}
