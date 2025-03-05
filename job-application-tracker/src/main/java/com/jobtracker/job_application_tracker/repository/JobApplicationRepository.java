package com.jobtracker.job_application_tracker.repository;
import com.jobtracker.job_application_tracker.model.JobApplication;
import com.jobtracker.job_application_tracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUser(Users user);
}
