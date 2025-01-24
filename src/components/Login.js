import React, { useState } from 'react'
import authservice from '../services/authservice';

const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');


  const handleLogin = async(e) => {
    e.preventDefault();
    try{
        const response = await authservice.login(username, password);
        const token = response.data;
        console.log("JWT Token: ", token)

        // Store the token in local storage
        localStorage.setItem('token', token);

        setSuccessMessage("Login Successfull!");
        // setErrorMessage("");


    }
    catch(error){
        console.log('Login Failed', error);
        setErrorMessage("Invalid username or password");
        // setSuccessMessage(" ");
    }
    
  };
  return (
    <div>
      <form onSubmit={handleLogin}>
        <input type="text" value={username} onChange={(e)=> setUsername(e.target.value)} placeholder="Username" required />
        <input type="password" value={password} onChange={(e)=> setPassword(e.target.value)} placeholder="password" required />
        <button type="submit"> Login </button>
      </form>
      {/* {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>} */}
    </div>
    
  )
}

export default Login

