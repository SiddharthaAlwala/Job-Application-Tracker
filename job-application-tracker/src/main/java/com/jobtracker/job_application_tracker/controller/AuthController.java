package com.jobtracker.job_application_tracker.controller;

import com.jobtracker.job_application_tracker.dto.AuthRequest;
import com.jobtracker.job_application_tracker.dto.AuthResponse;
import com.jobtracker.job_application_tracker.dto.RegisterRequest;
import com.jobtracker.job_application_tracker.model.Role;
import com.jobtracker.job_application_tracker.model.Users;
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
    private CustomUserDetailsService userDetailsService;

    @Autowired
    UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        Users newUser = new Users();
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(registerRequest.getPassword());
        //Set Role
        if (registerRequest.getRole() == null || registerRequest.getRole().isBlank()) {
            newUser.setRole(Role.ROLE_USER); // Default role
        } else {
            try {
                newUser.setRole(Role.valueOf("ROLE_" + registerRequest.getRole().toUpperCase()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid role. Allowed:ADMIN, USER");
            }
        }
        usersService.register(newUser);
        return ResponseEntity.ok("User Registered Successfully!");
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
