import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login({ onLogin }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    let userData;
    if (password === 'admin') {
      userData = { type: 'admin', email };
    } else if (password === 'customer') {
      userData = { type: 'customer', email };
    } else {
      setError('Login failed. Please check your credentials.');
      return; // Prevent further execution if credentials are wrong
    }
    onLogin(userData); // Pass the correct user data to the App component
    navigate('/');
  };

  return (
    <form onSubmit={handleLogin}>
      <div>
        <label>Email:</label>
        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
      </div>
      <div>
        <label>Password:</label>
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
      </div>
      {error && <p>{error}</p>}
      <button type="submit">Login</button>
    </form>
  );
}

export default Login;
