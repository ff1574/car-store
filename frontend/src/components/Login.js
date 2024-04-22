import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login({ onLogin }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    let userData;
    if (password === "admin") {
      userData = { type: "admin", email };
    } else if (password === "customer") {
      userData = { type: "customer", email };
    } else {
      setError("Login failed. Please check your credentials.");
      return; // Prevent further execution if credentials are wrong
    }
    onLogin(userData); // Pass the correct user data to the App component
    navigate("/");
  };

  const formStyle = {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "center",
    minHeight: "100vh", // Takes full height of the viewport
    margin: "0 auto",
    padding: "20px",
    boxSizing: "border-box",
    fontSize: "18px", // Larger font size
    background: `url('/login-background.png') center center fixed`, // Background image from the public directory
  };

  const inputStyle = {
    margin: "10px 0",
    padding: "10px",
    width: "300px", // Wider input fields
    fontSize: "16px",
    paddingTop: "10px",
  };
  const titleStyle = {
    fontSize: '38px', // Font size for title
    margin: '10px 0', // Spacing around the title
    color: '#333' // Optional: color for the title text
  };

  const subtitleStyle = {
    fontSize: '16px', // Font size for subtitle
    margin: '5px 0', // Tighter spacing for subtitle
    color: '#444', // Optional: color for the subtitle text
    paddingBottom: "20px"
  };

  return (
    <form onSubmit={handleLogin} style={formStyle}>
      <h1 style={titleStyle}>Login to Your Account</h1>
      <h2 style={subtitleStyle}>Please enter your credentials below.</h2>
      <div>
        <label>Email :</label>
        <br />
        <input
          type="email"å
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={inputStyle}
        />
      </div>
      <div>
        <label>Password :</label>
        <br />
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={inputStyle}
        />
      </div>
      {error && <p>{error}</p>}
      <button
        type="submit"
        style={{ padding: "10px 20px", fontSize: "18px", cursor: "pointer" }}
      >
        Login
      </button>
    </form>
  );
}

export default Login;
