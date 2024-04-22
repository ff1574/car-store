import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom"; // Import useParams to retrieve route parameters

function CarsComponent() {
  const { manufacturerId } = useParams(); // Retrieve the manufacturerId from the route
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [editCarId, setEditCarId] = useState(null); // To track which car is being edited
  const [editFormData, setEditFormData] = useState({}); // Form data for the car being edit

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

  const handleEditClick = (car) => {
    setEditCarId(car.carId);
    setEditFormData({
      carModel: car.carModel,
      carYear: car.carYear,
      carPrice: car.carPrice,
      carColor: car.carColor,
      carEngine: car.carEngine,
      carMileage: car.carMileage,
      carStock: car.stock,
    });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditFormData({
      ...editFormData,
      [name]: value,
    });
  };

  const handleSaveClick = async () => {
    try {
      await axios.put(
        `http://localhost:8080/api/car/${editCarId}`,
        editFormData
      );
      setCars(
        cars.map((car) =>
          car.carId === editCarId ? { ...car, ...editFormData } : car
        )
      );
      setEditCarId(null); // Exit edit mode
    } catch (error) {
      console.error("Failed to update car:", error);
    }
  };

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
      const response = await axios.put(
        `http://localhost:8080/api/car/${carId}`,
        updates
      );
      const updatedCars = cars.map((car) => {
        if (car.carId === carId) {
          // Assuming the response includes the updated car data
          return { ...car, ...response.data };
        }
        return car;
      });
      setCars(updatedCars); // Update the cars array with the modified car
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
          {editCarId === car.carId ? (
            // Editable inputs
            <>
              <input type="text" name="carModel" value={editFormData.carModel} onChange={handleInputChange} />
              <input type="number" name="carYear" value={editFormData.carYear} onChange={handleInputChange} />
              <input type="number" name="carPrice" value={editFormData.carPrice} onChange={handleInputChange} />
              <input type="text" name="carColor" value={editFormData.carColor} onChange={handleInputChange} />
              <input type="text" name="carEngine" value={editFormData.carEngine} onChange={handleInputChange} />
              <input type="number" name="carMileage" value={editFormData.carMileage} onChange={handleInputChange} />
              <input type="number" name="carStock" value={editFormData.carStock} onChange={handleInputChange} />
              <button onClick={handleSaveClick}>Save</button>
            </>
          ) : (
            // Display mode
            <>
              <h3>{car.carModel}</h3>
              <p><strong>Year:</strong> {car.carYear}</p>
              <p><strong>Price:</strong> ${car.carPrice}</p>
              <p><strong>Color:</strong> {car.carColor}</p>
              <p><strong>Engine:</strong> {car.carEngine}</p>
              <p><strong>Mileage:</strong> {car.carMileage} miles</p>
              <p><strong>Stock:</strong> {car.stock}</p>
              <button onClick={() => handleEditClick(car)}>Edit</button>
              <button onClick={() => handleDeleteCar(car.carId)}>Delete Car</button>
            </>
          )}
        </div>
      ))}
      <button onClick={handleAddCar}>Add New Car</button>
    </div>
  );
}


export default CarsComponent;
