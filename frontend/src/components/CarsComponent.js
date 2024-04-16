import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

function CarsComponent() {
  const [cars, setCars] = useState([]);
  const { manufacturerId } = useParams();
  const [isAdding, setIsAdding] = useState(false);
  const [newCar, setNewCar] = useState({
    carModel: '',
    carYear: '',
    carPrice: '',
    carColor: '',
    carEngine: '',
    carStockQuantity: '',
    manufacturer: {}
  });
  const [manufacturers, setManufacturers] = useState([
    { id: 1, name: 'Tesla' },
    { id: 2, name: 'Ford' },
    { id: 3, name: 'Chevrolet' }
  ]);

  useEffect(() => {
    fetchCars();
  }, [manufacturerId]);

  const fetchCars = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/manufacturer/${manufacturerId}`);
      const carsWithEditState = response.data.cars.map(car => ({
        ...car,
        isEditing: false,
        editData: { ...car } // Store initial data to revert back if needed
      }));
      setCars(carsWithEditState);
    } catch (error) {
      console.error(`Failed to fetch cars:`, error);
    }
  };
  

  const deleteCar = async (carId) => {
    if (window.confirm("Are you sure you want to delete this car?")) {
      try {
        await axios.delete(`http://localhost:8080/api/car/${carId}`);
        fetchCars();  // Refresh the list after deletion
      } catch (error) {
        console.error('Failed to delete the car:', error);
      }
    }
  };

  const addCar = async () => {
    try {
      await axios.post('http://localhost:8080/api/car', newCar);
      setIsAdding(false);
      setNewCar({ carModel: '', carYear: '', carPrice: '', carColor: '', carEngine: '', carStockQuantity: '', manufacturer: {} });
      fetchCars();
    } catch (error) {
      console.error('Failed to add a new car:', error);
    }
  };

  const updateCar = async (car) => {
    try {
      const updatedCarData = { ...car.editData }; // Assuming editData contains the updated fields
      const response = await axios.put(`http://localhost:8080/api/car/${car.carId}`, updatedCarData);
      if (response.status === 200) {
        // Refresh the list or handle the updated data directly
        fetchCars();
      } else {
        console.error('Failed to update the car');
      }
    } catch (error) {
      console.error('Failed to update the car:', error);
    }
  };
  


const handleInputChange = (e, index) => {
  const { name, value } = e.target;
  const updatedCars = cars.map((c, i) => i === index ? { ...c, [name]: value } : c);
  setCars(updatedCars);
};


  const handleManufacturerChange = (e, car) => {
    const manufacturerId = parseInt(e.target.value, 10);
    const manufacturer = manufacturers.find(m => m.id === manufacturerId);
    car.manufacturer = manufacturer || {};
    const updatedCars = [...cars]; // Update the state with new manufacturer
    setCars(updatedCars);
  };

  const toggleEdit = (index, isEditing) => {
    const updatedCars = cars.map((c, i) => i === index ? { ...c, isEditing } : c);
    setCars(updatedCars);
  };

  return (
    <div>
      <h1>Cars of Manufacturer {manufacturerId}</h1>
      {/* Your existing add car form here */}
      {!isAdding && <button onClick={() => setIsAdding(true)}>Add New Car</button>}
      {cars.map((car, index) => (
        <div key={car.carId} className="car-card">
          <h2>
            {car.isEditing ? (
              <input
                type="text"
                value={car.editData.carModel}
                onChange={e => handleInputChange(e, index, 'carModel')}
              />
            ) : (
              car.carModel
            )}
          </h2>
          <ul className="car-details">
            {/* All details to be editable */}
            <li>Year: {
              car.isEditing ? <input type="number" value={car.editData.carYear} onChange={e => handleInputChange(e, index, 'carYear')} /> : car.carYear
            }</li>
            {/* Add other details similarly */}
          </ul>
          {car.isEditing ? (
            <div>
              <button onClick={() => updateCar(car)}>Save Changes</button>
              <button onClick={() => toggleEdit(index, false)}>Cancel</button>
            </div>
          ) : (
            <button onClick={() => toggleEdit(index, true)}>Update</button>
          )}
          <button onClick={() => deleteCar(car.carId)}>Delete</button>
        </div>
      ))}
    </div>
  );
  
  
  }
  

export default CarsComponent;
