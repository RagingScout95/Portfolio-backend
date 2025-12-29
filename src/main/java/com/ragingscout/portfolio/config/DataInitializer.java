package com.ragingscout.portfolio.config;

import com.ragingscout.portfolio.entity.Admin;
import com.ragingscout.portfolio.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create default admin if none exists
        Optional<Admin> existingAdmin = adminRepository.findByUsername("admin");
        if (existingAdmin.isEmpty()) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Change this password!
            admin.setEmail("admin@ragingscout97.in");
            adminRepository.save(admin);
            System.out.println("Default admin created: username=admin, password=admin123");
            System.out.println("PLEASE CHANGE THE DEFAULT PASSWORD!");
        }
    }
}

