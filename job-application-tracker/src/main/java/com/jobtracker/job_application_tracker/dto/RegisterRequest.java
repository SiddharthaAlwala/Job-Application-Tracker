package com.jobtracker.job_application_tracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank
    @Size(min = 3, max = 20, message = "Username must be between 3-20 characters")
    private String username;

    @NotBlank
    @Size(min = 6, message = "Password must be atleast 6 characters")
    private String password;

    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
