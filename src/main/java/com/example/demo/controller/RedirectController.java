package com.example.demo.controller;

import com.example.demo.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for handling redirects from shortened URLs to original URLs.
 */
@Controller
public class RedirectController {

    private final UrlShortenerService urlShortenerService;

    @Autowired
    public RedirectController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * Redirect from a shortened URL to the original URL.
     *
     * @param id The ID part of the shortened URL
     * @return A RedirectView to the original URL, or a 404 if not found
     */
    @GetMapping("/{id}")
    public RedirectView redirect(@PathVariable String id) {
        return urlShortenerService.resolveUrl(id)
                .map(originalUrl -> {
                    RedirectView redirectView = new RedirectView(originalUrl);
                    redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
                    return redirectView;
                })
                .orElseGet(() -> {
                    RedirectView redirectView = new RedirectView("/error");
                    redirectView.setStatusCode(HttpStatus.NOT_FOUND);
                    return redirectView;
                });
    }
}