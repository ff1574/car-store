import React, { useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
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
  const [userEmail] = useState("");
  const [showSettings, setShowSettings] = useState(false); // State to toggle settings visibility
  const [appStyle, setAppStyle] = useState({
    backgroundColor: "#fff",
    fontSize: "16px",
  });

  const handleLogin = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
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

  // Styles for user type and settings button
  const userTypeStyle = {
    textAlign: "center",
    fontSize: "24px", // Larger font size
    textTransform: "uppercase",
    color: "#52abff", // Same blue color used in navbar
    margin: "10px 0",
  };

  const settingsButtonStyle = {
    padding: "10px 20px",
    backgroundColor: "#52abff",
    color: "white",
    fontSize: "18px",
    borderRadius: "5px",
    margin: "10px 10px",
    border: "none",
    cursor: "pointer",
    display: "block",
  };

  return (
    <div className="App" style={appStyle}>
      <header className="App-header">
        <h1 style={userTypeStyle}>{user.type}</h1>
        {user.type === "admin" && (
          <>
            <button
              onClick={() => setShowSettings(!showSettings)}
              style={settingsButtonStyle}
            >
              Settings
            </button>
            <Settings
              show={showSettings}
              onBackgroundChange={handleChangeBackgroundColor}
              onFontSizeChange={handleChangeFontSize}
            />
          </>
        )}

        <NavbarComponent onLogout={handleLogout} isAdmin={user.type === "admin"} />

        <Routes>
          <Route path="/" element={<ManufacturerComponent />} />
          <Route
            path="/manufacturers/:manufacturerId"
            element={<CarsComponent isAdmin={user.type === "admin"} />}
          />

          <Route
            path="/customers"
            element={
              <CustomersComponent
                email={userEmail}
                isAdmin={user.type === "admin"}
              />
            }
          />
          <Route
            path="/orders"
            element={
              user.type === "admin" ? <OrdersComponent /> : <Navigate to="/" />
            }
          />
        </Routes>
      </header>
    </div>
  );
}

export default App;
