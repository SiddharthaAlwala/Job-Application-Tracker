package com.jobtracker.job_application_tracker.controller;

import com.jobtracker.job_application_tracker.model.Users;
import com.jobtracker.job_application_tracker.repository.UserRepository;
import com.jobtracker.job_application_tracker.security.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

//    @Autowired
//    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, JwtUtils jwtUtils) {
//        this.authenticationManager = authenticationManager;
//        this.userRepository = userRepository;
//        this.jwtUtils = jwtUtils;
//    }

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expirationMs}")
    private Long jwtExpirationMs;

    @PostMapping("/register")
    public Users register(@RequestBody Users users){
        return userRepository.save(users);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users users){
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword())
            );

            // If authentication is successful, generate a JWT token
            if (authentication.isAuthenticated()) {
                String token = jwtUtils.generateToken(users.getUsername());
                return ResponseEntity.ok().body(Map.of("token", token));
            }
        } catch (AuthenticationException e) {
            // If authentication fails, return an error response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalied username or password");


        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }



}
