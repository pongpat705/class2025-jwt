package com.example.demo.service;

import com.example.demo.model.UrlMapping;
import com.example.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service for shortening URLs and resolving shortened URLs.
 */
@Service
public class UrlShortenerService {

    private final UrlRepository urlRepository;
    private final AtomicInteger counter = new AtomicInteger(1);
    private static final String SHORT_URL_PREFIX = "https://cc.co/";

    @Autowired
    public UrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /**
     * Shorten a URL.
     *
     * @param originalUrl The original URL to shorten
     * @return The URL mapping containing the shortened URL
     */
    public UrlMapping shortenUrl(String originalUrl) {
        // Generate a unique ID for the shortened URL
        String id = generateId();
        String shortUrl = SHORT_URL_PREFIX + id;

        // Create a new URL mapping
        UrlMapping mapping = new UrlMapping(
                id,
                originalUrl,
                shortUrl,
                LocalDateTime.now(),
                0
        );

        // Save the mapping to the repository
        return urlRepository.save(mapping);
    }

    /**
     * Resolve a shortened URL to its original URL.
     *
     * @param id The ID part of the shortened URL
     * @return An Optional containing the original URL if found, or empty if not found
     */
    public Optional<String> resolveUrl(String id) {
        return urlRepository.findById(id)
                .map(mapping -> {
                    // Increment the access count
                    mapping.setAccessCount(mapping.getAccessCount() + 1);
                    urlRepository.save(mapping);
                    return mapping.getOriginalUrl();
                });
    }

    /**
     * Generate a unique ID for a shortened URL.
     *
     * @return A unique ID
     */
    private String generateId() {
        // Simple implementation: use a counter and format as a 4-digit number
        int id = counter.getAndIncrement();
        return String.format("%04d", id);
    }
}