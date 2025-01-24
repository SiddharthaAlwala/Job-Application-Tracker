package com.jobtracker.job_application_tracker.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret; // Secret key from configuration (Base64 encoded)

    @Value("${jwt.expirationMs}")
    private Long jwtExpirationMs; // Token Expiration time in milliseconds

    private SecretKey secretKey; // HS512- compatible secret key

    @PostConstruct
    private void initSecretKey() {
        // Decode Base64 secret key or generate from a secure random string
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

}
