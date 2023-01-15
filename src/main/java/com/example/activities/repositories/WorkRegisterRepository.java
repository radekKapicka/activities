package com.example.activities.repositories;

import com.example.activities.model.Activity;
import com.example.activities.model.WorkRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("workRegisterRepository")
public interface WorkRegisterRepository extends JpaRepository<WorkRegister,Integer> {
}
