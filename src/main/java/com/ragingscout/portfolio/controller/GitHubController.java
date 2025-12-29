package com.ragingscout.portfolio.controller;

import com.ragingscout.portfolio.dto.GitHubRepo;
import com.ragingscout.portfolio.entity.Project;
import com.ragingscout.portfolio.service.GitHubService;
import com.ragingscout.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/github")
@CrossOrigin
public class GitHubController {

    @Autowired
    private GitHubService githubService;

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/repos/{username}")
    public ResponseEntity<List<GitHubRepo>> getUserRepositories(@PathVariable String username) {
        try {
            return ResponseEntity.ok(githubService.getUserRepositories(username));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/import/{username}")
    public ResponseEntity<List<Project>> importProjects(@PathVariable String username) {
        try {
            List<GitHubRepo> repos = githubService.getUserRepositories(username);
            List<Project> projects = repos.stream()
                .map(githubService::convertRepoToProject)
                .map(portfolioService::createProject)
                .collect(Collectors.toList());
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/import-single")
    public ResponseEntity<Project> importSingleProject(@RequestBody GitHubRepo repo) {
        try {
            Project project = githubService.convertRepoToProject(repo);
            return ResponseEntity.ok(portfolioService.createProject(project));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

