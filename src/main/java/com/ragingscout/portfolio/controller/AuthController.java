package com.ragingscout.portfolio.controller;

import com.ragingscout.portfolio.dto.LoginRequest;
import com.ragingscout.portfolio.dto.LoginResponse;
import com.ragingscout.portfolio.dto.SetPasswordRequest;
import com.ragingscout.portfolio.service.AuthService;
import com.ragingscout.portfolio.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestBody SetPasswordRequest request) {
        try {
            if (request.getUsername() == null || request.getUsername().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }
            
            // This endpoint only works if NO admin exists (initial setup only)
            authService.setPassword(
                request.getUsername(), 
                request.getPassword(), 
                request.getEmail()
            );
            return ResponseEntity.ok("Admin created successfully: " + request.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody SetPasswordRequest request,
            HttpServletRequest httpRequest) {
        try {
            // Extract username from JWT token
            String token = extractTokenFromRequest(httpRequest);
            if (token == null) {
                return ResponseEntity.status(401).body("Authentication required");
            }
            
            String authenticatedUsername = jwtService.getUsernameFromToken(token);
            
            // Only allow changing own password, or if username matches authenticated user
            String targetUsername = request.getUsername() != null ? request.getUsername() : authenticatedUsername;
            
            if (!authenticatedUsername.equals(targetUsername)) {
                return ResponseEntity.status(403).body("You can only change your own password");
            }
            
            if (request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }
            
            authService.changePassword(targetUsername, request.getPassword());
            return ResponseEntity.ok("Password changed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error changing password: " + e.getMessage());
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

