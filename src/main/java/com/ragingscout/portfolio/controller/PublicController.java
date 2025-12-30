package com.ragingscout.portfolio.controller;

import com.ragingscout.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@CrossOrigin
public class PublicController {

    // Update this version number each time you deploy to track deployments
    private static final String APP_VERSION = "1.0.2";
    private static final String BUILD_TIMESTAMP = "2025-12-30T04:25:00";

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("version", APP_VERSION);
        health.put("buildTimestamp", BUILD_TIMESTAMP);
        health.put("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        health.put("service", "portfolio-backend");
        return ResponseEntity.ok(health);
    }

    @GetMapping("/portfolio")
    public ResponseEntity<Map<String, Object>> getPortfolio() {
        Map<String, Object> portfolio = new HashMap<>();
        portfolio.put("profile", portfolioService.getProfile());
        portfolio.put("educations", portfolioService.getAllEducations());
        portfolio.put("skills", portfolioService.getAllSkills());
        portfolio.put("currentJob", portfolioService.getCurrentJob());
        portfolio.put("socialLinks", portfolioService.getAllSocialLinks());
        portfolio.put("experiences", portfolioService.getAllExperiences());
        portfolio.put("projects", portfolioService.getAllProjects());
        return ResponseEntity.ok(portfolio);
    }
}

