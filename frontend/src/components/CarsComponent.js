import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom"; // Import useParams to retrieve route parameters

function CarsComponent() {
  const { manufacturerId } = useParams(); // Retrieve the manufacturerId from the route
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchCars = async () => {
    const url = manufacturerId
      ? `http://localhost:8080/api/manufacturer/${manufacturerId}`
      : "http://localhost:8080/api/car";
    try {
      const response = await axios.get(url);
      if (manufacturerId) {
        setCars(response.data.cars || []); // Set cars if manufacturerId exists
      } else {
        setCars(response.data || []); // Set cars for all cars endpoint
      }
    } catch (error) {
      console.error("Failed to fetch cars:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCars();
    //eslint-disable-next-line
  }, [manufacturerId]);

  const handleAddCar = async () => {
    const newCar = {
      carModel: "Roadster Test",
      carYear: new Date().getFullYear(),
      carMileage: 100,
      carPrice: 129000,
      carColor: "Red",
      carEngine: "Electric",
      carStockQuantity: 3,
      manufacturerId,
    };

    console.log("New Car: ", newCar);

    try {
      const response = await axios.post(
        "http://localhost:8080/api/car",
        newCar
      );
      setCars([...cars, response.data]);
    } catch (error) {
      console.error("Failed to add car:", error);
    }
  };

  const handleEditCar = async (carId, updates) => {
    try {
      await axios.put(`http://localhost:8080/api/car/${carId}`, updates);
      fetchCars();
    } catch (error) {
      console.error("Failed to update car:", error);
    }
  };

  const handleDeleteCar = async (carId) => {
    try {
      await axios.delete(`http://localhost:8080/api/car/${carId}`);
      setCars(cars.filter((car) => car.carId !== carId));
    } catch (error) {
      console.error("Failed to delete car:", error);
    }
  };

  if (loading) {
    return <div>Loading cars...</div>;
  }

  return (
    <div>
      <h2>
        Cars{" "}
        {manufacturerId
          ? `of Manufacturer ${manufacturerId}`
          : "from All Manufacturers"}
      </h2>
      {cars.map((car) => (
        <div key={car.carId}>
          <h3>{car.carModel}</h3>
          <p>Year: {car.carYear}</p>
          <button
            onClick={() =>
              handleEditCar(car.carId, {
                ...car,
                carPrice: car.carPrice + 1000,
              })
            }
          >
            Increase Price
          </button>
          <button onClick={() => handleDeleteCar(car.carId)}>Delete Car</button>
        </div>
      ))}
      <button onClick={handleAddCar}>Add New Car</button>
    </div>
  );
}

export default CarsComponent;
