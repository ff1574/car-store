import React, { useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import Login from "./components/Login";
import ManufacturerComponent from "./components/ManufacturerComponent";
import CarsComponent from "./components/CarsComponent";
import CustomersComponent from "./components/CustomersComponent";
import OrdersComponent from "./components/OrdersComponent";
import NavbarComponent from "./components/NavbarComponent";
import MyOrdersComponent from "./components/MyOrdersComponent"; // Import MyOrdersComponent


import "./App.css";

function App() {
  const [user, setUser] = useState(null);
  const [userEmail] = useState("");
  const [appStyle] = useState({
    backgroundColor: "#fff",
    fontSize: "16px",
  });

  const handleLogin = (userData) => {
    setUser(userData);
  };

  const handleLogout = () => {
    setUser(null);
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

  return (
    <div className="App" style={appStyle}>
      <header className="App-header">
        <h1 style={userTypeStyle}>{user.type}</h1>

        <NavbarComponent onLogout={handleLogout} isAdmin={user.type === "admin"} />

        <Routes>
          <Route path="/" element={<ManufacturerComponent />} />
          <Route path="/manufacturers/:manufacturerId" element={<CarsComponent isAdmin={user.type === "admin"} user={user}/>} />
          <Route path="/customers" element={<CustomersComponent email={userEmail} isAdmin={user.type === "admin"} />} />
          <Route path="/orders" element={user.type === "admin" ? <OrdersComponent /> : <Navigate to="/" />} />
          <Route path="/myOrders" element={<MyOrdersComponent user={user} />} /> {/* Route for MyOrdersComponent */}
        </Routes>
      </header>
    </div>
  );
}
export default App;
