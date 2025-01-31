package com.jobtracker.job_application_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String jobTitle;
    private String status; // e.g., "Applied", "Interviewed", "Rejected", "Accepted"
    private String notes;
    private String resumeLink; // Link to the applicant's resume

    public Long getId() {
        return id;
    }

    public JobApplication setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public JobApplication setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public JobApplication setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public JobApplication setStatus(String status) {
        this.status = status;
        return this;
    }





    public String getNotes() {
        return notes;
    }

    public JobApplication setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
