import axios from 'axios';
const API_URL = 'http://localhost:8080/api/applications/';

const getAllApplications = () =>{
    const token = localStorage.getItem('token');
    return axios.get(API_URL,{
        headers:{ Authorization: `Bearer ${token}`},
    });
};

// const createApplication = async (application) => {
//     const token = localStorage.getItem('token');
//     if(!token || token.trim() === ""){
//         console.error("No token found, user might not be authenticated");
//         throw new Error ('Unauthorized request: No token found.');

//     }
//     try{
//         const response = await fetch(API_URL,{
//             method: "POST",
//             headers:{
//                 "Content-Type":"application/json",
//                 "Authorization": `Bearer ${token.trim()}`
//             },
//             body: JSON.stringify(application),
//             credentials: "include",
//         });
//         if(!response.ok){
//             throw new Error(`HTTP error! Status: ${response.status}`);
//         }
//         return await response.json();
//     }
//     catch(error){
//         console.error("Failed to create application", error );
//         throw error;
//     }
// }

const createApplication = async (application) => {
    const token = localStorage.getItem('token');
    // Make sure to prepend "Bearer " to the token
    const authHeader = token ? `Bearer ${token}` : '';
    try {
        const response = await axios.post(API_URL, application, {
            
            headers: {
                Authorization: authHeader, // Send the token with "Bearer "
                'Content-Type': 'application/json'
            },
            withCredentials: true,
        });
        return response.data;
    } catch (error) {
        console.error('Failed to create application', error);
        throw error;
    }
};

const updateApplication = (id, application) =>{
    const token = localStorage.getItem('token');
    return axios.put(API_URL + id, application,{
        headers:{Authorization: `Bearer ${token}`},
    });
};

const deleteApplication = (id) =>{
    const token = localStorage.getItem('token');
    return axios.delete(API_URL + id,{
        headers:{Authorization:`Bearer ${token}`},
    });
};

const jobApplicationService = {
    getAllApplications,
    createApplication,
    updateApplication,
    deleteApplication,
};

export default jobApplicationService;