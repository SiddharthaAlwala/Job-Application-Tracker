package com.jobtracker.job_application_tracker.controller;

import com.jobtracker.job_application_tracker.model.Users;
import com.jobtracker.job_application_tracker.repository.UserRepository;
import com.jobtracker.job_application_tracker.security.JwtUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    public String login(@RequestBody Users users){
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword())
            );

            // If authentication is successful, generate a JWT token
            if (authentication.isAuthenticated()) {
                return jwtUtils.generateToken(users.getUsername()); // Replace this with actual JWT generation logic
            }
        } catch (AuthenticationException e) {
            // If authentication fails, return an error response
            return "Invalid username or password";
        }

        return "Invalid username or password";
    }



}
