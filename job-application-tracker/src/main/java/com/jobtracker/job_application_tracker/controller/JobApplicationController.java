package com.jobtracker.job_application_tracker.controller;

import com.jobtracker.job_application_tracker.model.JobApplication;
import com.jobtracker.job_application_tracker.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:3000")
public class JobApplicationController {
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    @GetMapping
    public List<JobApplication> getAllApplications(){
        return jobApplicationRepository.findAll();

    }
    @PostMapping
    public JobApplication createApplication(@RequestBody JobApplication jobApplication) {
        return jobApplicationRepository.save(jobApplication);
    }

    @PutMapping("/{id}")
    public JobApplication updateApplication(@PathVariable Long id, @RequestBody JobApplication jobApplication) {
        jobApplication.setId(id);
        return jobApplicationRepository.save(jobApplication);
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
        jobApplicationRepository.deleteById(id);
    }

}
