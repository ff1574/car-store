// CarsComponent.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

function CarsComponent() {
  const [cars, setCars] = useState([]);

  useEffect(() => {
    const fetchCars = async () => {
      try {
        // Update the URL to match your actual API endpoint for fetching cars
        const response = await axios.get('http://localhost:8080/api/car');
        setCars(response.data);
      } catch (error) {
        console.error("Failed to fetch cars:", error);
      }
    };

    fetchCars();
  }, []);

  return (
    <div>
      <h1>Cars</h1>
      {cars.length > 0 ? (
        cars.map((car) => (
          <div key={car.carId} className="car-card">
            <h2>{car.carModel}</h2>
            <ul className="car-details">
              <li>Year: {car.carYear}</li>
              <li>Price: ${car.carPrice.toLocaleString()}</li>
              <li>Color: {car.carColor}</li>
              <li>Engine: {car.carEngine}</li>
              <li>Stock: {car.carStockQuantity}</li>
            </ul>
          </div>
        ))
      ) : (
        <p>Loading cars...</p>
      )}
    </div>
  );
}

export default CarsComponent;
