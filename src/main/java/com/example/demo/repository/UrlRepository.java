package com.example.demo.repository;

import com.example.demo.model.UrlMapping;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository for storing and retrieving URL mappings.
 * This is a simple in-memory implementation using a ConcurrentHashMap.
 */
@Repository
public class UrlRepository {
    
    private final Map<String, UrlMapping> mappings = new ConcurrentHashMap<>();
    
    /**
     * Save a URL mapping.
     *
     * @param mapping The URL mapping to save
     * @return The saved URL mapping
     */
    public UrlMapping save(UrlMapping mapping) {
        mappings.put(mapping.getId(), mapping);
        return mapping;
    }
    
    /**
     * Find a URL mapping by ID.
     *
     * @param id The ID of the URL mapping
     * @return An Optional containing the URL mapping if found, or empty if not found
     */
    public Optional<UrlMapping> findById(String id) {
        return Optional.ofNullable(mappings.get(id));
    }
    
    /**
     * Check if a URL mapping with the given ID exists.
     *
     * @param id The ID to check
     * @return true if a mapping with the given ID exists, false otherwise
     */
    public boolean existsById(String id) {
        return mappings.containsKey(id);
    }
}