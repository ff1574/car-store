import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Login({ onLogin }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // userData = { type: "customer", email };
  const handleLogin = async (e) => {
    e.preventDefault();
    var userData;
    // Create an axios post fetch with the user email and password as a JSON object
    try {
      // Send a POST request to the login endpoint
      const response = await axios.post("http://localhost:8080/api/login", {
        email,
        password,
      });
      if (response.status === 401) {
        setError("Invalid email or password");
        return;
      }

      if (response.data === "ROLE_CUSTOMER") {
        userData = { type: "customer", email };
      } else if (response.data === "ROLE_ADMINISTRATOR") {
        userData = { type: "admin", email };
      }

      console.log(userData);

      onLogin(userData); // Pass the correct user data to the App component
      navigate("/");
    } catch (error) {
      // Handle error
      setError(error.message);
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
    fontSize: "38px", // Font size for title
    margin: "10px 0", // Spacing around the title
    color: "#333", // Optional: color for the title text
  };

  const subtitleStyle = {
    fontSize: "16px", // Font size for subtitle
    margin: "5px 0", // Tighter spacing for subtitle
    color: "#444", // Optional: color for the subtitle text
    paddingBottom: "20px",
  };

  return (
    <form onSubmit={handleLogin} style={formStyle}>
      <h1 style={titleStyle}>Login to Your Account</h1>
      <h2 style={subtitleStyle}>Please enter your credentials below.</h2>
      <div>
        <label>Email :</label>
        <br />
        <input
          type="email"
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
