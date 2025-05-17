package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Model class representing a URL mapping.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {
    private String id;
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
    private long accessCount;
}