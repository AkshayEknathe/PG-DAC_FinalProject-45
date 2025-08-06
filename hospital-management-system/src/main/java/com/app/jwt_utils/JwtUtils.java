package com.app.jwt_utils;

import java.security.Key;
import java.util.Date;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

    @Value("${SECRET_KEY}")
    private String jwtSecret;

    @Value("${EXP_TIMEOUT}")
    private int jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Generates JWT token for both Customer and Trainer
    public String generateJwtToken(Authentication authentication) {
    	System.out.printf("Generating JWT token for authentication: {}", authentication);

        // Dynamically determine the principal (CustomerUserDetails or TrainerUserDetails)
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        // Extract role (first authority in the collection)
        String role = authentication.getAuthorities().stream()
                                    .findFirst()
                                    .map(authority -> authority.getAuthority())
                                    .orElse("UNKNOWN");

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // Username (email) as the subject
                .claim("role", role) // Add role as a claim
                .setIssuedAt(new Date()) // Issued time
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Expiration time
                .signWith(key, SignatureAlgorithm.HS512) // Signing algorithm
                .compact();
    }

    // Extracts username (email) from JWT token
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Extracts role from JWT token
    public String getRoleFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    // Validates the JWT token
    public boolean validateJwtToken(String jwtToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken); // Parses and validates the token
            return true;
        } catch (Exception e) {
        	System.out.printf("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }
}
