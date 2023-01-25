package com.example.activities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class ActivitiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiesApplication.class, args);
    }


}
