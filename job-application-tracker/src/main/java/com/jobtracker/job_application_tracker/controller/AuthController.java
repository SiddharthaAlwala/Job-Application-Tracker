package com.jobtracker.job_application_tracker.controller;

import com.jobtracker.job_application_tracker.model.Users;
import com.jobtracker.job_application_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public Users register(@RequestBody Users users){
        return userRepository.save(users);
    }
    @PostMapping("/login")
    public String login(@RequestBody Users users){
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(users.getUsername(), users.getPassword())
            );

            // If authentication is successful, generate a JWT token
            if (authentication.isAuthenticated()) {
                return "JWT Token"; // Replace this with actual JWT generation logic
            }
        } catch (AuthenticationException e) {
            // If authentication fails, return an error response
            return "Invalid username or password";
        }

        return "Invalid username or password";
    }

}
