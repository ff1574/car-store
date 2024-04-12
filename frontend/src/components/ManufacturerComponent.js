<<<<<<< HEAD
import React, { useState, useEffect } from "react";
import axios from "axios";
=======
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate for programmatic navigation
>>>>>>> 0c6ebf31b27b9f8474e76059a8c7bcdb6f30b9b5

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
<<<<<<< HEAD
            {/* Display Manufacturer Image */}
            <img
              src={`data:image/png;base64,${manufacturer.manufacturerImage}`}
              alt={manufacturer.manufacturerName}
              style={{ width: "200px", height: "auto" }}
            />
            <h3>Cars:</h3>
            <ul className="cars-list">
              {manufacturer.cars.map((car) => (
                <li key={car.carId}>
                  Model: {car.carModel}, Year: {car.carYear}, Price: $
                  {car.carPrice.toFixed(2)}, Color: {car.carColor}, Engine:{" "}
                  {car.carEngine}, Stock: {car.carStockQuantity}
                </li>
              ))}
            </ul>
          </div>
        ))
      ) : (
        <p>Loading manufacturers...</p>
      )}
=======
          ))
        ) : (
          <p>Loading manufacturers...</p>
        )}
      </div>
>>>>>>> 0c6ebf31b27b9f8474e76059a8c7bcdb6f30b9b5
    </div>
  );
}

export default ManufacturerComponent;
