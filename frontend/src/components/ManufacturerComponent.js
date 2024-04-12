import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for programmatic navigation

function ManufacturerComponent() {
  const [manufacturers, setManufacturers] = useState([]);
  const navigate = useNavigate(); // Initialize navigate function

  useEffect(() => {
    const fetchManufacturers = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/manufacturer');
        setManufacturers(response.data);
      } catch (error) {
        console.error('Failed to fetch manufacturers:', error);
      }
    };

    fetchManufacturers();
  }, []);

  // Function to navigate to the manufacturer's page
  const handleManufacturerClick = (manufacturerId) => {
    navigate(`/manufacturers/${manufacturerId}`); // Navigate to a dynamic route
  };

  return (
    <div>
      <h1>Manufacturers</h1>
      <div className="manufacturers-list">
        {manufacturers.length > 0 ? (
          manufacturers.map((manufacturer) => (
            <div key={manufacturer.manufacturerId} className="manufacturer-card" onClick={() => handleManufacturerClick(manufacturer.manufacturerId)}>
              <img src={manufacturer.manufacturerLogoUrl} alt={`${manufacturer.manufacturerName} Logo`} className="manufacturer-logo" />
              <h2>{manufacturer.manufacturerName}</h2>
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
