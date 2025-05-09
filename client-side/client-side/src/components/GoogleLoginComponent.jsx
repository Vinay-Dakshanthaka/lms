import React, { useEffect, useState } from 'react';
import { GoogleLogin } from '@react-oauth/google';
import axios from 'axios';

const GoogleLoginComponent = () => {
  const [userData, setUserData] = useState(null);

  const handleLoginSuccess = async (response) => {
    const idToken = response.credential;
    console.log('ID Token:', idToken);
    
   await axios.post('http://localhost:8080/api/oauth2/login', { idToken })
      .then((res) => {
        setUserData(res.data.data);
        console.log('Backend response:', res.data);
      })
      .catch((error) => {
        console.error('Error verifying token:', error);
      });
  };

  const handleLoginFailure = (error) => {
    console.error('Login failed:', error);
  };

  useEffect(() => {
    getHello();
  }, [])

  const getHello = async ()=>{
     const hello = await axios.get(`http://localhost:8080/api/oauth2/hello`)
     console.log("Response from hello :", hello)
  }
  

  return (
    <div>
      <h2>Sign in with Google</h2>
      <GoogleLogin
        onSuccess={handleLoginSuccess}
        onError={handleLoginFailure}
      />
      {userData && (
        <div>
          <h3>Welcome, {userData.name}</h3>
        </div>
      )}
    </div>
  );
};

export default GoogleLoginComponent;
