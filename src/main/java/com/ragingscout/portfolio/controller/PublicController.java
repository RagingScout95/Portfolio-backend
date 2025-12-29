package com.ragingscout.portfolio.controller;

import com.ragingscout.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
@CrossOrigin
public class PublicController {

    @Autowired
    private PortfolioService portfolioService;

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

