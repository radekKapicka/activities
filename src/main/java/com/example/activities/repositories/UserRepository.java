package com.example.activities.repositories;

import com.example.activities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);

}
