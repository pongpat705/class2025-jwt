package com.example.demo.controller;

import com.example.demo.model.UrlMapping;
import com.example.demo.service.UrlShortenerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for URL shortening and resolving endpoints.
 */
@RestController
@RequestMapping("/api/urls")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/shorten")
    public ResponseEntity<ShortenResponse> shortenUrlGet(@RequestBody ShortenRequest request) {
        System.out.println("Received request: " + request);
        return ResponseEntity.ok(null);
    }
    /**
     * Endpoint for shortening a URL.
     *
     * @param request The request containing the URL to shorten
     * @return A response containing the shortened URL and processing time
     */
    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(@RequestBody ShortenRequest request) {
        long startTime = System.currentTimeMillis();
        
        // Validate the request
        if (request.getUrl() == null || request.getUrl().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Shorten the URL
        UrlMapping mapping = urlShortenerService.shortenUrl(request.getUrl());
        
        // Calculate processing time
        long processingTime = System.currentTimeMillis() - startTime;
        
        // Create the response
        ShortenResponse response = new ShortenResponse(
                mapping.getOriginalUrl(),
                mapping.getShortUrl(),
                processingTime
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for resolving a shortened URL.
     *
     * @param id The ID part of the shortened URL
     * @return A response containing the original URL and processing time
     */
    @GetMapping("/resolve/{id}")
    public ResponseEntity<ResolveResponse> resolveUrl(@PathVariable String id) {
        long startTime = System.currentTimeMillis();
        
        // Resolve the URL
        return urlShortenerService.resolveUrl(id)
                .map(originalUrl -> {
                    // Calculate processing time
                    long processingTime = System.currentTimeMillis() - startTime;
                    
                    // Create the response
                    ResolveResponse response = new ResolveResponse(
                            originalUrl,
                            "https://cc.co/" + id,
                            processingTime
                    );
                    
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Request object for shortening a URL.
     */
    @Data
    public static class ShortenRequest {
        private String url;
    }

    /**
     * Response object for the shorten endpoint.
     */
    @Data
    public static class ShortenResponse {
        private final String originalUrl;
        private final String shortUrl;
        private final long processingTimeMs;
    }

    /**
     * Response object for the resolve endpoint.
     */
    @Data
    public static class ResolveResponse {
        private final String originalUrl;
        private final String shortUrl;
        private final long processingTimeMs;
    }
}