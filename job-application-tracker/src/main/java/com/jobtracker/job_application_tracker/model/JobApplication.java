package com.jobtracker.job_application_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String jobTitle;
    private String status;
    private LocalDate dateOfApplication;
    private LocalDate deadLine;
    private String notes;

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

    public LocalDate getDateOfApplication() {
        return dateOfApplication;
    }

    public JobApplication setDateOfApplication(LocalDate dateOfApplication) {
        this.dateOfApplication = dateOfApplication;
        return this;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public JobApplication setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
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
