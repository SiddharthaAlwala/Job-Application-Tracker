
import React, { useEffect, useState } from 'react';
import jobApplicationService from '../services/jobApplicationService';

const JobApplication = () => {
    const[applications, setApplications] = useState([]);
    const [newApplication, setNewApplication] = useState({
        companyName: '',
        jobTitle: '',
        status: '',
        dateOfApplication: '',
        deadline: '',
        notes: ''
    });

    useEffect(() => {
        fetchApplications();
    }, []);

    const fetchApplications = async () => {
        const response = await jobApplicationService.getAllApplications();
        setApplications(response.data);
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setNewApplication({ ...newApplication, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        await jobApplicationService.createApplication(newApplication);
        fetchApplications();
        setNewApplication({
            companyName: '',
            jobTitle: '',
            status: '',
            dateOfApplication: '',
            deadline: '',
            notes: ''
        });
    };

    return (
        <div>
            <h2>Job Applications</h2>
            <form onSubmit={handleSubmit}>
                <input type="text" name="companyName" value={newApplication.companyName} onChange={handleChange} placeholder="Company Name" required />
                <input type="text" name="jobTitle" value={newApplication.jobTitle} onChange={handleChange} placeholder="Job Title" required />
                <input type="text" name="status" value={newApplication.status} onChange={handleChange} placeholder="Status" required />
                <input type="date" name="dateOfApplication" value={newApplication.dateOfApplication} onChange={handleChange} required />
                <input type="date" name="deadline" value={newApplication.deadline} onChange={handleChange} required />
                <textarea name="notes" value={newApplication.notes} onChange={handleChange} placeholder="Notes"></textarea>
                <button type="submit">Add Application</button>
            </form>
            <ul>
                {applications.map(application => (
                    <li key={application.id}>
                        {application.companyName} - {application.jobTitle} - {application.status}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default JobApplication;