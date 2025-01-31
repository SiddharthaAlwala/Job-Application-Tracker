package com.jobtracker.job_application_tracker.service;

import com.jobtracker.job_application_tracker.model.JobApplication;
import com.jobtracker.job_application_tracker.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public List<JobApplication> getApplication(){
        return jobApplicationRepository.findAll();
    }

    public JobApplication createApplication(JobApplication jobApplication){
        return jobApplicationRepository.save(jobApplication);
    }

    public JobApplication updateApplication(Long id, JobApplication jobApplication){
        jobApplication.setId(id);
        return jobApplicationRepository.save(jobApplication);
    }

    public void deleteApplication(Long  id){
        jobApplicationRepository.deleteById(id);
    }

}
