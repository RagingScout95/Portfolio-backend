package com.ragingscout.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @ElementCollection(fetch = jakarta.persistence.FetchType.EAGER)
    @CollectionTable(name = "project_tech_stack", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tech")
    private List<String> techStack = new ArrayList<>();
    
    @Column(name = "live_url")
    private String liveUrl;
    
    @Column(name = "github_url")
    private String githubUrl;
    
    @Column(name = "demo_url")
    private String demoUrl;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "display_order")
    private Integer displayOrder = 0;
}

