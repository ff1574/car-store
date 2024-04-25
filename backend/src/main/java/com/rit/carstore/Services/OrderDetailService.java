package com.rit.carstore.Services;

import com.rit.carstore.Entities.OrderDetail;
import com.rit.carstore.Repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This is a service class for managing order details in the system.
@Service
public class OrderDetailService {

    // The repository for performing database operations on order details.
    private final OrderDetailRepository orderDetailRepository;

    // Constructor injection of the repository.
    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    // Method to find all order details.
    public List<OrderDetail> findAllOrderDetails() {
        return orderDetailRepository.findAll();
    }

    // Method to find an order detail by its ID.
    public Optional<OrderDetail> findOrderDetailById(Integer id) {
        return orderDetailRepository.findById(id);
    }

    // Method to save an order detail. If the order detail has an ID, it will be
    // updated. If it does not have an ID, it will be created.
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    // Method to delete an order detail by its ID.
    public void deleteOrderDetail(Integer id) {
        orderDetailRepository.deleteById(id);
    }
}