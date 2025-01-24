package com.jobtracker.job_application_tracker.repository;

import com.jobtracker.job_application_tracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
