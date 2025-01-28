package com.jobtracker.job_application_tracker.repository;

import com.jobtracker.job_application_tracker.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobApplicationRepository extends JpaRepository <JobApplication, Long>{

}
