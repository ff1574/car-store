// ManufacturerComponent.js
import React, { useState, useEffect } from "react";
import axios from "axios";

function ManufacturerComponent() {
  const [manufacturers, setManufacturers] = useState([]);

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

  return (
    <div>
      <h1>Manufacturers</h1>
      {manufacturers.length > 0 ? (
        manufacturers.map((manufacturer) => (
          <div key={manufacturer.manufacturerId} className="manufacturer-card">
            <div className="manufacturer-header">
              <h2>
                {manufacturer.manufacturerName} (
                {manufacturer.manufacturerCountry})
              </h2>
              <a
                href={manufacturer.manufacturerWebsite}
                target="_blank"
                rel="noopener noreferrer"
                className="manufacturer-website"
              >
                Website
              </a>
            </div>
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
    </div>
  );
}

export default ManufacturerComponent;
