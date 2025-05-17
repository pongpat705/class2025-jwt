package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for serving the home page.
 */
@Controller
public class HomeController {

    /**
     * Serve the home page.
     *
     * @return The name of the home page template
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }
}