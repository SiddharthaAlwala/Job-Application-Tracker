// src/components/Login.js
import React, { useState } from 'react';
import authService from '../services/authService';
import {useNavigate} from 'react-router-dom'

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error , setError] = useState('');
    const navigate = useNavigate();
   

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await authService.login(username, password);
            const token = response.data.token
            console.log("JWT Token:", token);

            //Store the token in local storage
            localStorage.setItem('token', token);
            navigate('/home');
        } catch (error) {
            console.error('Login failed', error);
            setError('Invalide username or password');
        }
    };

    return (
       <div>
       <h2>Login</h2>
       {error && <p style={{color:'red'}}>{error}</p>}
       <form onSubmit={handleLogin}>
            <input type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)} 
            placeholder='Username'
            required/>

            <input type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder='Password'
            required />

            <button type='submit'>Login</button>
       </form>
       </div>
    );
};

export default Login;