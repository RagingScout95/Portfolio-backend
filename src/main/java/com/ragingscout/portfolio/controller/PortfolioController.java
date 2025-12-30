package com.ragingscout.portfolio.controller;

import com.ragingscout.portfolio.entity.*;
import com.ragingscout.portfolio.service.PortfolioService;
import com.ragingscout.portfolio.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private FileStorageService fileStorageService;

    // Profile endpoints
    @GetMapping("/profile")
    public ResponseEntity<Profile> getProfile() {
        return ResponseEntity.ok(portfolioService.getProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) {
        return ResponseEntity.ok(portfolioService.updateProfile(profile));
    }

    // Education endpoints
    @GetMapping("/educations")
    public ResponseEntity<List<Education>> getAllEducations() {
        return ResponseEntity.ok(portfolioService.getAllEducations());
    }

    @PostMapping("/educations")
    public ResponseEntity<Education> createEducation(@RequestBody Education education) {
        return ResponseEntity.ok(portfolioService.createEducation(education));
    }

    @PutMapping("/educations/{id}")
    public ResponseEntity<Education> updateEducation(@PathVariable Long id, @RequestBody Education education) {
        return ResponseEntity.ok(portfolioService.updateEducation(id, education));
    }

    @DeleteMapping("/educations/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id) {
        portfolioService.deleteEducation(id);
        return ResponseEntity.ok().build();
    }

    // Skill endpoints
    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(portfolioService.getAllSkills());
    }

    @PostMapping("/skills")
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        return ResponseEntity.ok(portfolioService.createSkill(skill));
    }

    @PutMapping("/skills/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        return ResponseEntity.ok(portfolioService.updateSkill(id, skill));
    }

    @DeleteMapping("/skills/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        portfolioService.deleteSkill(id);
        return ResponseEntity.ok().build();
    }

    // CurrentJob endpoints
    @GetMapping("/current-job")
    public ResponseEntity<CurrentJob> getCurrentJob() {
        return ResponseEntity.ok(portfolioService.getCurrentJob());
    }

    @PutMapping("/current-job")
    public ResponseEntity<CurrentJob> updateCurrentJob(@RequestBody CurrentJob job) {
        return ResponseEntity.ok(portfolioService.updateCurrentJob(job));
    }

    // SocialLink endpoints
    @GetMapping("/social-links")
    public ResponseEntity<List<SocialLink>> getAllSocialLinks() {
        return ResponseEntity.ok(portfolioService.getAllSocialLinks());
    }

    @PostMapping("/social-links")
    public ResponseEntity<SocialLink> createSocialLink(@RequestBody SocialLink link) {
        return ResponseEntity.ok(portfolioService.createSocialLink(link));
    }

    @PutMapping("/social-links/{id}")
    public ResponseEntity<SocialLink> updateSocialLink(@PathVariable Long id, @RequestBody SocialLink link) {
        return ResponseEntity.ok(portfolioService.updateSocialLink(id, link));
    }

    @DeleteMapping("/social-links/{id}")
    public ResponseEntity<Void> deleteSocialLink(@PathVariable Long id) {
        portfolioService.deleteSocialLink(id);
        return ResponseEntity.ok().build();
    }

    // Experience endpoints
    @GetMapping("/experiences")
    public ResponseEntity<List<Experience>> getAllExperiences() {
        return ResponseEntity.ok(portfolioService.getAllExperiences());
    }

    @PostMapping("/experiences")
    public ResponseEntity<Experience> createExperience(@RequestBody Experience experience) {
        return ResponseEntity.ok(portfolioService.createExperience(experience));
    }

    @PutMapping("/experiences/{id}")
    public ResponseEntity<Experience> updateExperience(@PathVariable Long id, @RequestBody Experience experience) {
        return ResponseEntity.ok(portfolioService.updateExperience(id, experience));
    }

    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        portfolioService.deleteExperience(id);
        return ResponseEntity.ok().build();
    }

    // Project endpoints
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(portfolioService.getAllProjects());
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(portfolioService.createProject(project));
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        return ResponseEntity.ok(portfolioService.updateProject(id, project));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        portfolioService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/projects/reorder")
    public ResponseEntity<Void> reorderProjects(@RequestBody Map<String, List<Object>> request) {
        List<Object> projectIdsObj = request.get("projectIds");
        // Convert to Long list (handles both Integer and Long from JSON)
        List<Long> projectIds = projectIdsObj.stream()
            .map(id -> {
                if (id instanceof Long) {
                    return (Long) id;
                } else if (id instanceof Number) {
                    return ((Number) id).longValue();
                } else {
                    throw new IllegalArgumentException("Invalid project ID type: " + id.getClass().getName());
                }
            })
            .collect(Collectors.toList());
        portfolioService.reorderProjects(projectIds);
        return ResponseEntity.ok().build();
    }

    // File upload endpoint
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = fileStorageService.storeFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}

