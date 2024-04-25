import React, { useState, useEffect } from "react";
import axios from "axios";

function OrdersComponent() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch both orders and orderDetails concurrently
        const [ordersRes, orderDetailsRes] = await Promise.all([
          axios.get("http://localhost:8080/api/order"),
          axios.get("http://localhost:8080/api/orderDetail"),
        ]);

        const ordersData = ordersRes.data;
        const orderDetailsData = orderDetailsRes.data;

        // Assume orderDetailsData includes an implicit/explicit match to orders (e.g., via orderId)
        // Here we merge details into their respective orders
        const mergedData = ordersData.map((order) => ({
          ...order,
          // Find and integrate orderDetails into this order
          // This assumes there's a direct or inferable match between order IDs and orderDetail IDs
          orderDetails: orderDetailsData.filter(
            (detail) => detail.orderDetailId === order.orderId
          ),
        }));

        setOrders(mergedData);
      } catch (error) {
        console.error("Failed to fetch data:", error);
      }
    };

    fetchData();
  }, []);

  return (
    <div>
      <h1>Orders</h1>
      {orders.length > 0 ? (
        orders.map((order) => (
          <div key={order.orderId} className="order-card">
            <h2>Order ID: {order.orderId}</h2>
            <ul>
              <li>Order Date: {order.orderDate}</li>
              <li>Order Total: ${order.orderTotal.toLocaleString()}</li>
              {order.orderDetails && order.orderDetails.length > 0 ? (
                order.orderDetails.map((detail) => (
                  <ul key={detail.orderDetailId}>
                    <li>Car Quantity: {detail.carQuantity}</li>
                    <li>
                      Price Per Unit: ${detail.carPricePerUnit.toLocaleString()}
                    </li>
                  </ul>
                ))
              ) : (
                <li>No detailed information available.</li>
              )}
            </ul>
          </div>
        ))
      ) : (
        <p>Loading orders...</p>
      )}
    </div>
  );
}

export default OrdersComponent;
