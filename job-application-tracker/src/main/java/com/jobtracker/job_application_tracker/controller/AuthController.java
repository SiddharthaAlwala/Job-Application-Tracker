package com.jobtracker.job_application_tracker.controller;

import com.jobtracker.job_application_tracker.dto.AuthRequest;
import com.jobtracker.job_application_tracker.dto.AuthResponse;
import com.jobtracker.job_application_tracker.dto.RegisterRequest;
import com.jobtracker.job_application_tracker.model.Users;
import com.jobtracker.job_application_tracker.repository.UsersRepository;
import com.jobtracker.job_application_tracker.security.JwtUtils;
import com.jobtracker.job_application_tracker.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import jakarta.websocket.OnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String role = registerRequest.getRole();

        if(usersRepository.findByUsername(username).isPresent()){
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists!"));

        }

        // ✅ Hash the password before storing it
        String hashedPassword = passwordEncoder.encode(password);

        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setRole(role);

        usersRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully!");
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken(authRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

}
