package com.ragingscout.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "experiences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String role;
    
    @Column(nullable = false)
    private String company;
    
    @Column(nullable = false)
    private String fromDate;
    
    @Column(nullable = false)
    private String toDate;
    
    @ElementCollection(fetch = jakarta.persistence.FetchType.EAGER)
    @CollectionTable(name = "experience_descriptions", joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "description", columnDefinition = "TEXT")
    private List<String> descriptions = new ArrayList<>();
    
    @Column(name = "display_order")
    private Integer displayOrder = 0;
}

