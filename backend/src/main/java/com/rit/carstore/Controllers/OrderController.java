package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Order;
import com.rit.carstore.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        return orderService.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        return orderService.findOrderById(id)
                .map(existingOrder -> {
                    order.setOrderId(id);
                    return ResponseEntity.ok(orderService.saveOrder(order));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

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
