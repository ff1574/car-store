package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Order;
import com.rit.carstore.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This is a REST controller for managing orders in the system.
@RestController
@RequestMapping("/api/order")
public class OrderController {

    // Service class for handling business logic related to orders.
    private final OrderService orderService;

    // Dependency injection of the order service.
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Endpoint for getting all orders.
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    // Endpoint for getting an order by its ID.
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        return orderService.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for creating a new order.
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    // Endpoint for updating an existing order.
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        return orderService.findOrderById(id)
                .map(existingOrder -> {
                    order.setOrderId(id);
                    return ResponseEntity.ok(orderService.saveOrder(order));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for deleting an order.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        return orderService.findOrderById(id)
                .map(order -> {
                    orderService.deleteOrder(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}