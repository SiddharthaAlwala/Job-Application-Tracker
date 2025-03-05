package com.jobtracker.job_application_tracker.controller;
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


    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<JobApplication>> getAllApplications(Authentication authentication){
        String username = authentication.getName();
        return ResponseEntity.ok(jobApplicationService.getAllApplications(username));

    }
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<JobApplication> createApplication(Authentication authentication, @RequestBody JobApplication jobApplication) {
        String username = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED).body(jobApplicationService.createApplication(username, jobApplication));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> updateApplication(Authentication authentication, @PathVariable Long id, @RequestBody JobApplication jobApplication) {
        String username = authentication.getName();
        try {
            JobApplication updatedApplication = jobApplicationService.updateApplication(username, id, jobApplication);
            return ResponseEntity.ok(updatedApplication);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> deleteApplication(Authentication authentication, @PathVariable Long id) {
        String username = authentication.getName();
        try {
            jobApplicationService.deleteApplication(username,id);
            return ResponseEntity.ok(Map.of("message", "Job Application deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        }
    }

}
