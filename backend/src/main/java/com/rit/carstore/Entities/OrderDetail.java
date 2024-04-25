package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

// This is an entity class for managing order details in the system.
@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetail extends Auditable {
    // The primary key for the OrderDetail entity. It is auto-generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;

    // Many-to-one relationship with the Order entity.
    // The 'order_id' column in the 'order_details' table is a foreign key
    // referencing the Order entity.
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;

    // Many-to-one relationship with the Car entity.
    // The 'car_id' column in the 'order_details' table is a foreign key referencing
    // the Car entity.
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "car_id")
    private Car car;

    // The quantity of the car in the order detail.
    private Integer carQuantity;

    // The price per unit of the car in the order detail. It is a decimal number
    // with a precision of 10 and a scale of 2.
    @Column(precision = 10, scale = 2)
    private BigDecimal carPricePerUnit;
}