package com.ragingscout.portfolio.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ragingscout.portfolio.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        boolean shouldSkip = requestPath.startsWith("/api/public/") || 
                            requestPath.equals("/api/auth/login") || 
                            requestPath.equals("/api/auth/set-password");
        if (shouldSkip) {
            System.out.println("JWT Filter: Skipping filter for path: " + requestPath);
        }
        return shouldSkip;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        String authHeader = request.getHeader("Authorization");
        
        System.out.println("JWT Filter: Processing request - Path: " + requestPath + ", Has Auth Header: " + (authHeader != null));
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            try {
                boolean isValid = jwtService.validateToken(token);
                System.out.println("JWT Filter: Token validation result: " + isValid);
                
                if (isValid) {
                    String username = jwtService.getUsernameFromToken(token);
                    System.out.println("JWT Filter: Token valid for user: " + username);
                    
                    UsernamePasswordAuthenticationToken authentication = 
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    System.out.println("JWT Filter: Authentication set in SecurityContext. Authenticated: " + 
                        (SecurityContextHolder.getContext().getAuthentication() != null));
                } else {
                    // Token is invalid or expired
                    System.out.println("JWT Filter: Invalid or expired token for path: " + requestPath);
                }
            } catch (Exception e) {
                // Token parsing failed
                System.out.println("JWT Filter: Token parsing error for path: " + requestPath + " - " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // No token provided for protected endpoint
            if (requestPath.startsWith("/api/admin/") || requestPath.startsWith("/api/auth/change-password")) {
                System.out.println("JWT Filter: No token provided for protected path: " + requestPath);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}

