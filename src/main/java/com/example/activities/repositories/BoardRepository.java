package com.example.activities.repositories;

import com.example.activities.model.Board;
import com.example.activities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Integer> {
}
