// App.js
import React, { useState, useEffect } from "react";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./components/Login";
import ManufacturerComponent from "./components/ManufacturerComponent";
import CarsComponent from "./components/CarsComponent";
import CustomersComponent from "./components/CustomersComponent";
import OrdersComponent from "./components/OrdersComponent";
import NavbarComponent from "./components/NavbarComponent";
import Settings from "./components/Settings"; // Import Settings component

import "./App.css";

function App() {
  const [user, setUser] = useState(null);
  const [userEmail, setUserEmail] = useState("");
  const [showSettings, setShowSettings] = useState(false); // State to toggle settings visibility
  const [appStyle, setAppStyle] = useState({
    backgroundColor: "#fff",
    fontSize: "16px",
  });

  const handleLogin = (userData) => {
    setUser(userData); // Assuming userData contains information about the user, including their role
    // For simplicity, you might just use a flag like isAdmin for admin users
  };

  // Optionally, handle logout
  const handleLogout = () => {
    setUser(null);
    // Also, clear any stored authentication tokens or user data as needed
  };

  const handleChangeBackgroundColor = (color) => {
    setAppStyle((prevStyle) => ({ ...prevStyle, backgroundColor: color }));
  };

  const handleChangeFontSize = (size) => {
    setAppStyle((prevStyle) => ({ ...prevStyle, fontSize: size }));
  };

  if (!user) {
    return <Login onLogin={handleLogin} />;
  }

  // Corrected style prop usage
  return (
    <div className="App" style={appStyle}>
      <header className="App-header">
        <h1>{user.type}</h1>
        {user.type === "admin" && (
          <>
            <button onClick={() => setShowSettings(!showSettings)}>
              Settings
            </button>
            <Settings
              show={showSettings}
              onBackgroundChange={handleChangeBackgroundColor}
              onFontSizeChange={handleChangeFontSize}
            />
          </>
        )}

        {user && <NavbarComponent onLogout={handleLogout} />}

        <Routes>
          <Route path="/" element={<ManufacturerComponent />} />

          <Route
            path="/manufacturers/:manufacturerId"
            element={<CarsComponent />}
          />

          <Route
            path="/customers"
            element={<CustomersComponent email={userEmail} />}
          />

          <Route
            path="/orders"
            element={user.isAdmin ? <OrdersComponent /> : <Navigate to="/" />}
          />
        </Routes>
      </header>
    </div>
  );
}

export default App;
