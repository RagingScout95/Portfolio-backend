package com.ragingscout.portfolio.service;

import com.ragingscout.portfolio.config.ApplicationProperties;
import com.ragingscout.portfolio.dto.GitHubRepo;
import com.ragingscout.portfolio.entity.Project;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitHubService {

    private final WebClient webClient;

    public GitHubService(@NonNull ApplicationProperties applicationProperties) {
        String apiUrl = applicationProperties.getGithub().getApi().getUrl();
        this.webClient = WebClient.builder()
            .baseUrl(apiUrl != null ? apiUrl : "https://api.github.com")
            .build();
    }

    public List<GitHubRepo> getUserRepositories(String username) {
        try {
            GitHubRepo[] repos = webClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .bodyToMono(GitHubRepo[].class)
                .block();

            return repos != null ? Arrays.asList(repos) : new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch repositories: " + e.getMessage());
        }
    }

    public Project convertRepoToProject(GitHubRepo repo) {
        Project project = new Project();
        project.setName(repo.getName());
        project.setDescription(repo.getDescription() != null ? repo.getDescription() : "");
        project.setGithubUrl(repo.getHtml_url());
        project.setLiveUrl(repo.getHomepage());
        
        List<String> techStack = new ArrayList<>();
        if (repo.getLanguage() != null) {
            techStack.add(repo.getLanguage());
        }
        if (repo.getTopics() != null) {
            techStack.addAll(Arrays.asList(repo.getTopics()));
        }
        project.setTechStack(techStack);
        
        return project;
    }
}

