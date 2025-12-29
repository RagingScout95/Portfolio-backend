package com.ragingscout.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "educations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String degree;
    
    @Column(nullable = false)
    private String institute;
    
    private String year;
    
    @Column(name = "display_order")
    private Integer displayOrder = 0;
}

