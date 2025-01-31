package com.jobtracker.job_application_tracker.controller;

import com.jobtracker.job_application_tracker.model.JobApplication;
import com.jobtracker.job_application_tracker.repository.JobApplicationRepository;
import com.jobtracker.job_application_tracker.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {
    @Autowired
    private JobApplicationService jobApplicationService;


    @GetMapping

    public List<JobApplication> getAllApplications(){
        return jobApplicationService.getApplication();

    }
    @PostMapping

    public JobApplication createApplication(@RequestBody JobApplication jobApplication) {
        return jobApplicationService.createApplication(jobApplication);
    }

    @PutMapping("/{id}")

    public JobApplication updateApplication(@PathVariable Long id, @RequestBody JobApplication jobApplication) {

        return jobApplicationService.updateApplication(id, jobApplication);
    }

    @DeleteMapping("/{id}")

    public void deleteApplication(@PathVariable Long id) {
        jobApplicationService.deleteApplication(id);
    }

}
