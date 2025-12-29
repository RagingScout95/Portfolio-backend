package com.ragingscout.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "current_jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String company;
    
    private String since;
    
    @Column(columnDefinition = "TEXT")
    private String description;
}

