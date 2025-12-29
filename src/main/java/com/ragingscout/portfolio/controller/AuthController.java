package com.ragingscout.portfolio.controller;

import com.ragingscout.portfolio.dto.LoginRequest;
import com.ragingscout.portfolio.dto.LoginResponse;
import com.ragingscout.portfolio.dto.SetPasswordRequest;
import com.ragingscout.portfolio.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthService authService;

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
            
            authService.setPassword(
                request.getUsername(), 
                request.getPassword(), 
                request.getEmail()
            );
            return ResponseEntity.ok("Password set successfully for user: " + request.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error setting password: " + e.getMessage());
        }
    }
}

