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

@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;
    private final CarRepository carRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderDetailController(OrderDetailService orderDetailService, CarRepository carRepository, OrderRepository orderRepository) {
        this.orderDetailService = orderDetailService;
        this.carRepository = carRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<OrderDetail> getAllOrderDetails() {
        return orderDetailService.findAllOrderDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Integer id) {
        return orderDetailService.findOrderDetailById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public OrderDetail createOrderDetail(@RequestBody OrderDetail orderDetail, @RequestParam Integer carId, @RequestParam Integer orderId) {
        System.out.println(orderDetail.toString() + " " + carId + " " + orderId);
        Car car = carRepository.findById(carId).get();
        Order order = orderRepository.findById(orderId).get();
        orderDetail.setCar(car);
        orderDetail.setOrder(order);
        return orderDetailService.saveOrderDetail(orderDetail);
    }

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