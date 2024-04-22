import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";

function CarsComponent() {
  const { manufacturerId } = useParams();
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [editCarId, setEditCarId] = useState(null);
  const [editFormData, setEditFormData] = useState({});

  const fetchCars = async () => {
    const url = manufacturerId
      ? `http://localhost:8080/api/manufacturer/${manufacturerId}`
      : "http://localhost:8080/api/car";
    try {
      const response = await axios.get(url);
      setCars(manufacturerId ? response.data.cars || [] : response.data || []);
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

  const handleDeleteCar = async (carId) => {
    try {
      await axios.delete(`http://localhost:8080/api/car/${carId}`);
      setCars(cars.filter((car) => car.carId !== carId));
    } catch (error) {
      console.error("Failed to delete car:", error);
    }
  };

  const handleEditCar = (car) => {
    setEditCarId(car.carId);
    setEditFormData({
      carModel: car.carModel,
      carYear: car.carYear,
      carPrice: car.carPrice,
      carColor: car.carColor,
      carEngine: car.carEngine,
      carMileage: car.carMileage,
      carStockQuantity: car.stock,
      manufacturerId: manufacturerId,
    });
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setEditFormData({ ...editFormData, [name]: value });
  };

  const handleSave = async () => {
    try {
      const updatedCar = {
        ...editFormData,
        manufacturerId,
      };
      const response = await axios.put(`http://localhost:8080/api/car/${editCarId}`, updatedCar);
      const updatedCars = cars.map((car) => car.carId === editCarId ? response.data : car);
      setCars(updatedCars);
      setEditCarId(null); // Exit edit mode
    } catch (error) {
      console.error("Failed to update car:", error);
    }
  };

  if (loading) {
    return <div>Loading cars...</div>;
  }

  return (
    <div>
      <h2>
        Cars {manufacturerId ? `of Manufacturer ${manufacturerId}` : "from All Manufacturers"}
      </h2>
      {cars.map((car) => (
        <div key={car.carId}>
          {editCarId === car.carId ? (
            <>
              <input type="text" name="carModel" value={editFormData.carModel} onChange={handleInputChange} />
              <input type="number" name="carYear" value={editFormData.carYear} onChange={handleInputChange} />
              <input type="number" name="carPrice" value={editFormData.carPrice} onChange={handleInputChange} />
              <input type="text" name="carColor" value={editFormData.carColor} onChange={handleInputChange} />
              <input type="text" name="carEngine" value={editFormData.carEngine} onChange={handleInputChange} />
              <input type="number" name="carMileage" value={editFormData.carMileage} onChange={handleInputChange} />
              <input type="number" name="carStockQuantity" value={editFormData.carStockQuantity} onChange={handleInputChange} />
              <button onClick={handleSave}>Save</button>
            </>
          ) : (
            <>
              <h3>{car.carModel}</h3>
              <p><strong>Year:</strong> {car.carYear}</p>
              <p><strong>Price:</strong> ${car.carPrice}</p>
              <p><strong>Color:</strong> {car.carColor}</p>
              <p><strong>Engine:</strong> {car.carEngine}</p>
              <p><strong>Mileage:</strong> {car.carMileage} miles</p>
              <p><strong>Stock:</strong> {car.carStockQuantity}</p>
              <button onClick={() => handleEditCar(car)}>Edit</button>
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
