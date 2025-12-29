package com.ragingscout.portfolio.service;

import com.ragingscout.portfolio.dto.LoginRequest;
import com.ragingscout.portfolio.dto.LoginResponse;
import com.ragingscout.portfolio.entity.Admin;
import com.ragingscout.portfolio.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(request.getUsername());
        
        if (adminOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), adminOpt.get().getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(request.getUsername());
        return new LoginResponse(token, request.getUsername());
    }

    public void createAdmin(String username, String password, String email) {
        if (adminRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Admin already exists");
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setEmail(email);
        adminRepository.save(admin);
    }

    public void changePassword(String username, String newPassword) {
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isEmpty()) {
            throw new RuntimeException("Admin not found");
        }
        
        Admin admin = adminOpt.get();
        admin.setPassword(passwordEncoder.encode(newPassword));
        adminRepository.save(admin);
    }

    public void setPassword(String username, String password, String email) {
        // Security: Only allow creating admin if NO admin exists (initial setup)
        long adminCount = adminRepository.count();
        
        if (adminCount > 0) {
            // Admin exists - this endpoint should not be used
            // Password changes should be done through authenticated endpoint
            throw new RuntimeException("Admin already exists. Use authenticated endpoint to change password.");
        }
        
        // Only allow if no admin exists (initial setup only)
        Optional<Admin> adminOpt = adminRepository.findByUsername(username);
        if (adminOpt.isPresent()) {
            throw new RuntimeException("Admin already exists");
        }
        
        // Create new admin (only allowed during initial setup)
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setEmail(email != null ? email : username + "@ragingscout97.in");
        adminRepository.save(admin);
    }
}

