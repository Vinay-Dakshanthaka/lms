import React from 'react'
import { GoogleOAuthProvider } from '@react-oauth/google'; // Import GoogleOAuthProvider
import GoogleLoginComponent from './components/GoogleLoginComponent'; // Import your GoogleLoginComponent
import PaginatedUserList from './components/PaginatedUserList';

function App() {
  // const clientId = '25562083860-drilebton07s4nd1n7qlse2rgtcvfdrv.apps.googleusercontent.com'; lmsOAuth
  const clientId = '25562083860-ntumf7l83oh8n742qdehtonv99oks8bf.apps.googleusercontent.com';  //lms2

  return (
    <>
      <GoogleOAuthProvider clientId={clientId}> {/* Wrap the component with GoogleOAuthProvider */}
      <div>
        <h1>Welcome to React App</h1>
        <GoogleLoginComponent  /> {/* Your Google login component */}
      </div>
    </GoogleOAuthProvider>
    <PaginatedUserList />
    </>
  );
}

export default App;
