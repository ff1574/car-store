package com.rit.carstore.Services;

import com.rit.carstore.Entities.Order;
import com.rit.carstore.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This is a service class for managing orders in the system.
@Service
public class OrderService {

    // The repository for performing database operations on orders.
    private final OrderRepository orderRepository;

    // Constructor injection of the repository.
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Method to find all orders.
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    // Method to find an order by its ID.
    public Optional<Order> findOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    // Method to save an order. If the order has an ID, it will be updated. If it
    // does not have an ID, it will be created.
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Method to delete an order by its ID.
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}