package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository for storing and retrieving users.
 * This is a simple in-memory implementation using a ConcurrentHashMap.
 */
@Repository
public class UserRepository {
    
    private final Map<String, User> users = new ConcurrentHashMap<>();
    
    /**
     * Save a user.
     *
     * @param user The user to save
     * @return The saved user
     */
    public User save(User user) {
        users.put(user.getUsername(), user);
        return user;
    }
    
    /**
     * Find a user by username.
     *
     * @param username The username of the user
     * @return An Optional containing the user if found, or empty if not found
     */
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }
    
    /**
     * Check if a user with the given username exists.
     *
     * @param username The username to check
     * @return true if a user with the given username exists, false otherwise
     */
    public boolean existsByUsername(String username) {
        return users.containsKey(username);
    }
}