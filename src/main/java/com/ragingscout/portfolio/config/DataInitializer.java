package com.ragingscout.portfolio.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ragingscout.portfolio.entity.Admin;
import com.ragingscout.portfolio.repository.AdminRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Admin accounts should be created/updated via API: POST /api/auth/set-password
        // This allows setting passwords without direct database access
        Optional<Admin> existingAdmin = adminRepository.findByUsername("admin");
        if (existingAdmin.isEmpty()) {
            System.out.println("INFO: No admin user found.");
            System.out.println("Use the API to create admin: POST /api/auth/set-password");
            System.out.println("Required fields: username, password, email (optional)");
        }
    }
}

