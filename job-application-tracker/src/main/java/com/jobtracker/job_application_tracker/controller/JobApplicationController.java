package com.jobtracker.job_application_tracker.controller;

import com.jobtracker.job_application_tracker.model.ApplicationStatus;
import com.jobtracker.job_application_tracker.model.JobApplication;
import com.jobtracker.job_application_tracker.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    @Autowired
    private JobApplicationService jobApplicationService;

    // Admin can see all the applications
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<JobApplication>> getAllApplications(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(jobApplicationService.getAllApplications(username));

    }

    //Users can create their application
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<JobApplication> createApplication(Authentication authentication, @RequestBody JobApplication jobApplication) {
        String username = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(jobApplicationService.createApplication(username, jobApplication));
    }

    //Users can withdraw their own application
    @PutMapping("/{id}/withdraw")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<JobApplication> withdrawApplication(Authentication authentication, @PathVariable Long id) {
        String username = authentication.getName();
        return ResponseEntity.ok(jobApplicationService.withDrawnApplication(username, id));
    }


    // Admin can change the status of the application except "APPLIED and WITHDRAWN"
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<JobApplication> updateApplicationStatus(Authentication authentication, @PathVariable Long id, @RequestBody Map<String, String> statusMap) {
        String adminUsername = authentication.getName();
        ApplicationStatus newStatus = ApplicationStatus.valueOf(statusMap.get("status"));
        return ResponseEntity.ok(jobApplicationService.updateJobApplicationStatus(id, newStatus, adminUsername));
    }

    // User can edit his application except except status.
    @PutMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('ROLE_USER)")
    public ResponseEntity<JobApplication> editApplication(Authentication authentication, @PathVariable Long id, @RequestBody JobApplication updatedApplication) {
        String username = authentication.getName();
        return ResponseEntity.ok(jobApplicationService.editApplication(username, id, updatedApplication));
    }


}
