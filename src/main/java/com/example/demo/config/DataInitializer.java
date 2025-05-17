package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Component for initializing data when the application starts.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;

    public DataInitializer(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        // Create a default user for testing
        try {
            userService.registerUser("admin", "password");
            System.out.println("Default user created: admin/password");
        } catch (Exception e) {
            System.out.println("Default user already exists");
        }
    }
}
