package com.jobtracker.job_application_tracker.service;

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

    public List<JobApplication> getAllApplications(String username) {
        Users users = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        if (users.getRole() == Role.ROLE_ADMIN) {
            return jobApplicationRepository.findAll(); // Admin can see all applications.
        } else {
            return jobApplicationRepository.findByUser(users);
        }
    }

    public JobApplication createApplication(String username, JobApplication jobApplication) {
        Users users = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found exception"));
        jobApplication.setUser(users); // Assosiate the application with the user.
        return jobApplicationRepository.save(jobApplication);
    }

    public JobApplication updateApplication(String username, Long id, JobApplication jobApplication) {
        // Checking if the job application exists
        JobApplication existingApplication = jobApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Application Not Found"));

        if (!existingApplication.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to update this application");
        }

        //update fields (except createdAt)
        existingApplication.setCompanyName(jobApplication.getCompanyName());
        existingApplication.setJobTitle(jobApplication.getJobTitle());
        existingApplication.setJobDescription(jobApplication.getJobDescription());
        existingApplication.setStatus(jobApplication.getStatus());
        existingApplication.setResumeLink(jobApplication.getResumeLink());

        return jobApplicationRepository.save(existingApplication);
    }

    public void deleteApplication(String username, Long id) {

        JobApplication jobApplication = jobApplicationRepository.findById(id).orElseThrow(() -> new RuntimeException("Job Application not found"));
        if (!jobApplication.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not authorized to delete");
        }
        jobApplicationRepository.deleteById(id);
    }

}
