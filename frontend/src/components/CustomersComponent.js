// CustomersComponent.js
import React, { useState, useEffect } from "react";
import axios from "axios";

function CustomersComponent() {
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    const fetchCustomers = async () => {
      try {
        // Adjust the URL to your API endpoint for fetching customers
        const response = await axios.get("http://localhost:8080/api/customer");
        setCustomers(response.data);
      } catch (error) {
        console.error("Failed to fetch customers:", error);
      }
    };

    fetchCustomers();
  }, []);

  return (
    <div>
      <h1>Customers</h1>
      {customers.length > 0 ? (
        customers.map((customer) => (
          <div key={customer.customerId} className="customer-card">
            <h2>{customer.customerName}</h2>
            <ul className="customer-details">
              <li>Email: {customer.customerEmail}</li>
              <li>Phone: {customer.customerPhone}</li>
              <li>Address: {customer.customerAddress}</li>
            </ul>
          </div>
        ))
      ) : (
        <p>Loading customers...</p>
      )}
    </div>
  );
}

export default CustomersComponent;
