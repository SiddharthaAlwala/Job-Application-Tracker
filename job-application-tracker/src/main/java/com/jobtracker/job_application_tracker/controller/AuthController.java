package com.jobtracker.job_application_tracker.controller;

import com.jobtracker.job_application_tracker.dto.AuthRequest;
import com.jobtracker.job_application_tracker.dto.AuthResponse;
import com.jobtracker.job_application_tracker.model.Role;
import com.jobtracker.job_application_tracker.model.Users;
import com.jobtracker.job_application_tracker.repository.UsersRepository;
import com.jobtracker.job_application_tracker.security.JwtUtils;
import com.jobtracker.job_application_tracker.service.CustomUserDetailsService;
import com.jobtracker.job_application_tracker.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Users users) {
        try {
            if (users.getRole() == null || (!users.getRole().equals(Role.ROLE_ADMIN) && !users.getRole().equals(Role.ROLE_USER))) {
                users.setRole(Role.ROLE_USER); // Default to ROLE_USER if no role is provided
            }// Default Role Assigned
            usersService.register(users);
            return ResponseEntity.ok("User registered successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.ok("Usrename already exists with given username: " + users.getUsername()); //Return error message if user exists
        }
    }

    //    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")

    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        //Setting the authentication object explicitly in securitycontextholder.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate  JWT after successfull authentication
        String jwt = jwtUtils.generateToken(authRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt));

    }

}
