import axios from 'axios';
const API_URL = 'http://localhost:8080/api/applications/';

const getAllApplications = () =>{
    return axios.get(API_URL)
};

const createApplication = (application) => {
    return axios.post(API_URL, application);
};

const updateApplication = (id, application) =>{
    return axios.put(API_URL + id, application)
};

const deleteApplication = (id) =>{
    return axios.delete(API_URL + id);
};

export default{
    getAllApplications,
    createApplication,
    updateApplication,
    deleteApplication,
};