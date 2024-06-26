import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";

function CarsComponent({ isAdmin, user }) {
  const { manufacturerId } = useParams();
  const [manufacturer, setManufacturer] = useState([]);
  const [cars, setCars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [editCarId, setEditCarId] = useState(null);
  const [editFormData, setEditFormData] = useState({});
  const [carImage, setcarImage] = useState(null);
  const formData = new FormData();

  const fetchCars = async () => {
    const url = `http://localhost:8080/api/manufacturer/${manufacturerId}`;
    try {
      const response = await axios.get(url);
      setManufacturer(response.data);
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
    formData.append("carModel", "Test Car");
    formData.append("carYear", new Date().getFullYear());
    formData.append("carMileage", 100);
    formData.append("carPrice", 129000);
    formData.append("carColor", "Red");
    formData.append("carEngine", "Electric");
    formData.append("carStockQuantity", 3);
    formData.append("manufacturerId", manufacturerId);
    if (carImage) {
      formData.append("carImage", carImage);
    }

    console.log("formData", formData.get("carImage"));
    try {
      const response = await axios.post(
        `http://localhost:8080/api/car?manufacturerId=${manufacturerId}`,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
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
      carStockQuantity: car.carStockQuantity,
      carImage: carImage,
      manufacturerId: manufacturerId,
    });
  };

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setEditFormData({ ...editFormData, [name]: value });
  };

  const handleImageChange = (event) => {
    setcarImage(event.target.files[0]);
  };

  const handleSave = async () => {
    const data = new FormData();
    Object.keys(editFormData).forEach((key) => {
      data.append(key, editFormData[key]);
    });

    try {
      const response = await axios.put(
        `http://localhost:8080/api/car/${editCarId}`,
        data,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      const updatedCars = cars.map((car) =>
        car.carId === editCarId ? response.data : car
      );
      setCars(updatedCars);
      setEditCarId(null); // Exit edit mode
    } catch (error) {
      console.error("Failed to update car:", error);
    }
  };

  const handleBuyCar = async (car) => {
    if (!user || !user.email) {
      alert("User is not logged in or email is not available.");
      return;
    }

    try {
      // Fetch customer details based on email
      const response = await axios.get(
        `http://localhost:8080/api/customer/email`,
        {
          params: { email: user.email },
        }
      );

      if (response.status !== 200 || !response.data) {
        alert("Failed to retrieve customer details.");
        return;
      }

      const customerId = response.data;
      const order = {
        customerId: customerId,
        orderDate: new Date().toISOString().slice(0, 10),
        orderTotal: car.carPrice,
        orderStatus: "Completed",
      };

      console.log("Order to be placed:", order);
      // Place the order using the fetched customer ID
      const orderResponse = await axios.post(
        "http://localhost:8080/api/order",
        order
      );
      console.log("Order created:", orderResponse.data);

      const orderDetails = {
        carQuantity: 1,
        carPricePerUnit: car.carPrice,
      };

      console.log("Order details to be placed:", orderDetails);

      const orderDetailsResponse = await axios.post(
        `http://localhost:8080/api/orderDetail?carId=${car.carId}&orderId=${orderResponse.data.orderId}`,
        orderDetails
      );
      console.log("Order details created:", orderDetailsResponse.data);
    } catch (error) {
      console.error("Failed to place order:", error);
    }
  };

  if (loading) {
    return <div>Loading cars...</div>;
  }
  const carsListStyle = {
    display: "flex",
    flexWrap: "wrap",
    justifyContent: "center",
    alignItems: "center",
    gap: "20px",
  };

  const carCardStyle = {
    cursor: "pointer",
    textAlign: "center",
    marginBottom: "20px",
    padding: "20px",
    border: "1px solid #ccc",
    boxShadow: "0 2px 6px rgba(0,0,0,0.1)",
    borderRadius: "8px",
  };

  const carImageStyle = {
    width: "100px",
    height: "100px",
    marginBottom: "10px",
  };

  const carNameStyle = {
    fontSize: "30px",
    fontWeight: "bold",
    color: "#52abff", // Apply the previously used blue color
  };
  const editModeStyle = {
    display: "flex",
    flexDirection: "column", // Ensures items are stacked vertically
    gap: "10px", // Adds space between each form control
    width: "100%", // Ensures the flex container takes full width
    alignItems: "flex-start", // Aligns items to the start of the flex container
  };
  const inputStyle = {
    width: "100%", // Ensures input takes up the full width
    padding: "10px", // Good padding for better text visibility
    marginBottom: "10px", // Adds space below each input for separation
    borderRadius: "5px", // Optionally, adds rounded corners for better aesthetics
    border: "1px solid #ccc", // Gives a subtle border to the inputs
  };
  return (
    <div>
      <h2
        style={{
          textAlign: "center",
          color: "#52abff",
          fontSize: "40px",
          margin: "40px 0",
        }}
      >
        Cars{" "}
        {manufacturerId
          ? `of ${manufacturer.manufacturerName}`
          : "from All Manufacturers"}
      </h2>
      {isAdmin && (
        <button
          onClick={handleAddCar} // This is where the handleAddCar function is bound to the button click event
          style={{
            display: "block",
            margin: "20px auto",
            padding: "10px 20px",
            fontSize: "16px",
            color: "#fff",
            backgroundColor: "#52abff",
            border: "none",
            borderRadius: "5px",
            cursor: "pointer",
          }}
        >
          Add New Car
        </button>
      )}

      <div style={carsListStyle}>
        {loading ? (
          <p>Loading cars...</p>
        ) : (
          cars.map((car) => (
            <div key={car.carId} style={carCardStyle}>
              {editCarId === car.carId && isAdmin ? (
                <div style={editModeStyle}>
                  <input
                    type="text"
                    name="carModel"
                    value={editFormData.carModel}
                    onChange={handleInputChange}
                    style={inputStyle}
                  />
                  <input
                    type="number"
                    name="carYear"
                    value={editFormData.carYear}
                    onChange={handleInputChange}
                    style={inputStyle}
                  />
                  <input
                    type="number"
                    name="carPrice"
                    value={editFormData.carPrice}
                    onChange={handleInputChange}
                    style={inputStyle}
                  />
                  <input
                    type="text"
                    name="carColor"
                    value={editFormData.carColor}
                    onChange={handleInputChange}
                    style={inputStyle}
                  />
                  <input
                    type="text"
                    name="carEngine"
                    value={editFormData.carEngine}
                    onChange={handleInputChange}
                    style={inputStyle}
                  />
                  <input
                    type="number"
                    name="carMileage"
                    value={editFormData.carMileage}
                    onChange={handleInputChange}
                    style={inputStyle}
                  />
                  <input
                    type="number"
                    name="carStockQuantity"
                    value={editFormData.carStockQuantity}
                    onChange={handleInputChange}
                    style={inputStyle}
                  />
                  <input
                    type="file"
                    accept="image/*"
                    onChange={handleImageChange}
                    style={{ display: "block", margin: "10px 0" }}
                  />

                  <button onClick={handleSave}>Save</button>
                </div>
              ) : (
                // Display mode
                <>
                  <img
                    src={`data:image/png;base64,${car.carImage}`} // Assuming each car has a carImage property
                    alt={`${car.carModel}`}
                    style={carImageStyle}
                  />
                  <h3 style={carNameStyle}>{car.carModel}</h3>
                  <p>
                    <strong>Year:</strong> {car.carYear}
                  </p>
                  <p>
                    <strong>Price:</strong> ${car.carPrice}
                  </p>
                  <p>
                    <strong>Color:</strong> {car.carColor}
                  </p>
                  <p>
                    <strong>Engine:</strong> {car.carEngine}
                  </p>
                  <p>
                    <strong>Mileage:</strong> {car.carMileage} miles
                  </p>
                  <p>
                    <strong>Stock:</strong> {car.carStockQuantity}
                  </p>
                  {!isAdmin && (
                    <>
                      <button onClick={() => handleBuyCar(car)}>Buy</button>
                    </>
                  )}
                  {isAdmin && (
                    <>
                      <button onClick={() => handleEditCar(car)}>Edit</button>
                      <button onClick={() => handleDeleteCar(car.carId)}>
                        Delete Car
                      </button>
                    </>
                  )}
                </>
              )}
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default CarsComponent;
