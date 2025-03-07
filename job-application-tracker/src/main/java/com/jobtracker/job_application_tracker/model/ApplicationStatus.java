package com.jobtracker.job_application_tracker.model;

public enum ApplicationStatus {

    APPLIED,    // Default when user applies
    WITHDRAWN,  // User can withdraw application
    INTERVIEW_SCHEDULED,  //Admin updates when interview is scheduled
    OFFER_RECEIVED,  // Admin updates if user gets an offer
    REJECTED  // Admin updtaes if the application is rejected
}
