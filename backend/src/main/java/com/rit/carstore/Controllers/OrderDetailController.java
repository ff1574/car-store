package com.rit.carstore.Controllers;

import com.rit.carstore.Entities.Car;
import com.rit.carstore.Entities.Order;
import com.rit.carstore.Entities.OrderDetail;
import com.rit.carstore.Repositories.CarRepository;
import com.rit.carstore.Repositories.OrderRepository;
import com.rit.carstore.Services.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// This is a REST controller for managing order details in the system.
@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailController {

    // Service class for handling business logic related to order details.
    private final OrderDetailService orderDetailService;
    // Repository class for accessing the database for car-related operations.
    private final CarRepository carRepository;
    // Repository class for accessing the database for order-related operations.
    private final OrderRepository orderRepository;

    // Dependency injection of the order detail service and repositories.
    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService, CarRepository carRepository,
            OrderRepository orderRepository) {
        this.orderDetailService = orderDetailService;
        this.carRepository = carRepository;
        this.orderRepository = orderRepository;
    }

    // Endpoint for getting all order details.
    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.findAllOrderDetails();
    }

    // Endpoint for getting an order detail by its ID.
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Integer id) {
        return orderDetailService.findOrderDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for creating a new order detail. The car and order IDs are provided
    // as request parameters.
    @PostMapping
    public OrderDetail createOrderDetail(@RequestBody OrderDetail orderDetail, @RequestParam Integer carId,
            @RequestParam Integer orderId) {
        // Fetch the car and order from the database.
        Car car = carRepository.findById(carId).get();
        Order order = orderRepository.findById(orderId).get();
        // Set the car and order in the order detail.
        orderDetail.setCar(car);
        orderDetail.setOrder(order);
        return orderDetailService.saveOrderDetail(orderDetail);
    }

    // Endpoint for updating an existing order detail.
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Integer id,
            @RequestBody OrderDetail orderDetail) {
        return orderDetailService.findOrderDetailById(id)
                .map(existingOrderDetail -> {
                    orderDetail.setOrderDetailId(id);
                    return ResponseEntity.ok(orderDetailService.saveOrderDetail(orderDetail));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint for deleting an order detail.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Integer id) {
        return orderDetailService.findOrderDetailById(id)
                .map(orderDetail -> {
                    orderDetailService.deleteOrderDetail(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}