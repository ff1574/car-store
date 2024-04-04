// OrdersComponent.js
import React, { useState, useEffect } from 'react';
import axios from 'axios';

function OrdersComponent() {
  const [orders, setOrders] = useState([]);

  useEffect(() => {
    const fetchOrdersAndDetails = async () => {
      try {
        // Fetch the orders
        const ordersResponse = await axios.get('http://localhost:8080/api/orders');
        const ordersData = ordersResponse.data;

        // For each order, fetch its details and add them to the order object
        const ordersWithDetails = await Promise.all(ordersData.map(async (order) => {
          const detailsResponse = await axios.get(`http://localhost:8080/api/order_details/${order.orderId}`);
          return { ...order, orderDetails: detailsResponse.data };
        }));

        setOrders(ordersWithDetails);
      } catch (error) {
        console.error("Failed to fetch orders and details:", error);
      }
    };

    fetchOrdersAndDetails();
  }, []);

  return (
    <div>
      <h1>Orders</h1>
      {orders.length > 0 ? (
        orders.map((order) => (
          <div key={order.orderId} className="order-card">
            <h2>Order ID: {order.orderId}</h2>
            <ul className="order-details">
              <li>Order Date: {order.orderDate}</li>
              <li>Order Total: ${order.orderTotal.toLocaleString()}</li>
              {order.orderDetails && order.orderDetails.length > 0 && (
                <div>
                  Order Details:
                  <ul>
                    {order.orderDetails.map((detail) => (
                      <li key={detail.orderDetailId}>
                        Car ID: {detail.carId}, Quantity: {detail.carQuantity}, Price per Unit: ${detail.carPricePerUnit.toLocaleString()}
                      </li>
                    ))}
                  </ul>
                </div>
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
