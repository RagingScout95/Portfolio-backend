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
}

