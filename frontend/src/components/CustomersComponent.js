import React, { useState, useEffect } from "react";
import axios from "axios";

function CustomersComponent() {
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    const fetchCustomers = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/customer");
        setCustomers(response.data);
      } catch (error) {
        console.error("Failed to fetch customers:", error);
      }
    };

    fetchCustomers();
  }, []);

  if (!customers.length) {
    return <p>Loading customers...</p>;
  }

  return (
    <div>
      <h1>Customers</h1>
      {customers.map((customer) => (
        <div key={customer.customerId} className="customer-card">
          <h2>{customer.customerName}</h2>
          <p>Email: {customer.customerEmail}</p>
          <p>Phone: {customer.customerPhone}</p>
          {/* Add additional customer details here */}
        </div>
      ))}
    </div>
  );
}

export default CustomersComponent;
