package com.jobtracker.job_application_tracker.service;

import com.jobtracker.job_application_tracker.model.ApplicationStatus;
import com.jobtracker.job_application_tracker.model.JobApplication;
import com.jobtracker.job_application_tracker.model.Role;
import com.jobtracker.job_application_tracker.model.Users;
import com.jobtracker.job_application_tracker.repository.JobApplicationRepository;
import com.jobtracker.job_application_tracker.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UsersRepository usersRepository;

    //  Allow admins to view all job applications, and users can view only their applications
    public List<JobApplication> getAllApplications(String username) {
        Users users = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Admin not found"));
        if (users.getRole() == Role.ROLE_ADMIN) {
            return jobApplicationRepository.findAll(); // Admin can see all applications.
        } else {
            return jobApplicationRepository.findByUser(users); // users can see their own applications
        }
    }

    // logic for user can create their application
    public JobApplication createApplication(String username, JobApplication jobApplication) {
        Users users = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found exception"));
        jobApplication.setUser(users); // Assosiate the application with the user.
        jobApplication.setStatus(ApplicationStatus.APPLIED); // Default status
        return jobApplicationRepository.save(jobApplication);
    }

    // Users can only withdraw their own application
    public JobApplication withDrawnApplication(String username, Long id) {
        JobApplication application = jobApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Application not Found"));
        if (!application.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to withdraw this application");
        }
        application.setStatus(ApplicationStatus.WITHDRAWN);
        return jobApplicationRepository.save(application);
    }
    

    // Only admins can Update Application status.
    public JobApplication updateJobApplicationStatus(Long id, ApplicationStatus newStatus, String adminUsername) {
        Users admin = usersRepository.findByUsername(adminUsername).orElseThrow(() -> new RuntimeException("Admin not found"));
        if (admin.getRole() != Role.ROLE_ADMIN) {
            throw new RuntimeException("Only admins can update job application status");
        }
        JobApplication application = jobApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Job application not found"));
        application.setStatus(newStatus);

        return jobApplicationRepository.save(application);
    }

    // Users can only edit their own applications(Except status)
    public JobApplication editApplication(String username, Long id, JobApplication updatedApplication) {
        JobApplication existingApplication = jobApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Application not found"));
        // Check if the logged in user own this application
        if (!existingApplication.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to edit this application.");
        }

        // Only allow users to update specific fields, NOT status
        existingApplication.setCompanyName(updatedApplication.getCompanyName());
        existingApplication.setJobTitle(updatedApplication.getJobTitle());
        existingApplication.setJobDescription(updatedApplication.getJobDescription());

        return jobApplicationRepository.save(existingApplication);
    }


}
