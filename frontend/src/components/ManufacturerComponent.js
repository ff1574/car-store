import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function ManufacturerComponent() {
  const [manufacturers, setManufacturers] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchManufacturers = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/manufacturer"
        );
        setManufacturers(response.data);
      } catch (error) {
        console.error("Failed to fetch manufacturers:", error);
      }
    };

    fetchManufacturers();
  }, []);

  // Function to navigate to the manufacturer's page
  const handleManufacturerClick = (manufacturerId) => {
    navigate(`/manufacturers/${manufacturerId}`);
  };

  const manufacturersListStyle = {
    display: "flex", // Use flexbox to lay out the cards horizontally
    flexWrap: "wrap", // Allow wrapping of cards if there's not enough horizontal space
    justifyContent: "center", // Center-align the cards horizontally
    alignItems: "center", // Center-align the cards vertically
    gap: "20px", // Add space between the cards
  };

  const manufacturerCardStyle = {
    cursor: "pointer",
    textAlign: "center", // Center-align all contents of the card
    marginBottom: "20px", // Add some margin at the bottom for spacing
    paddingRight: "40px",
  };

  const manufacturerImageStyle = {
    width: "100px", // Smaller width for images
    height: "100px", // Set height to maintain aspect ratio, adjust as needed
    marginBottom: "10px", // Space between image and name
  };

  const manufacturerNameStyle = {
    fontSize: "30px", // Larger font size for names
    fontWeight: "bold", // Optional: make the font weight bold
  };
  const headingStyle = {
    textAlign: "center",
    fontSize: "60px", // Larger font size for names
    marginTop: "150px",
    marginBottom: "100px",
    color: "#52abff", // Apply the previously used blue color
    margin: "20px 0",
  };

  return (
    <div>
      <h1 style={headingStyle}>Manufacturers</h1>
      <div style={manufacturersListStyle}>
        {manufacturers.length > 0 ? (
          manufacturers.map((manufacturer) => (
            <div
              key={manufacturer.manufacturerId}
              style={manufacturerCardStyle}
              onClick={() =>
                handleManufacturerClick(manufacturer.manufacturerId)
              }
            >
              <img
                src={`data:image/png;base64,${manufacturer.manufacturerImage}`}
                alt={`${manufacturer.manufacturerName} Logo`}
                style={manufacturerImageStyle}
              />
              <h2 style={manufacturerNameStyle}>
                {manufacturer.manufacturerName}
              </h2>
            </div>
          ))
        ) : (
          <p>Loading manufacturers...</p>
        )}
      </div>
    </div>
  );
}

export default ManufacturerComponent;
