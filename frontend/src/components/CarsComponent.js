// CarsComponent.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

function CarsComponent() {
  const [cars, setCars] = useState([]);
  const { manufacturerId } = useParams();

  useEffect(() => {
    const fetchCarsByManufacturer = async () => {
      try {
        // Adjust the endpoint to include the manufacturerId to fetch cars for that manufacturer
        const response = await axios.get(`http://localhost:8080/api/manufacturer/${manufacturerId}`);
        setCars(response.data.cars); // Assuming the response structure includes a cars array
      } catch (error) {
        console.error(`Failed to fetch cars for manufacturer ${manufacturerId}:`, error);
      }
    };

    fetchCarsByManufacturer();
  }, [manufacturerId]);

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
