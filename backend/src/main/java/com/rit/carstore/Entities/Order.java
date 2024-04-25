package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

// This is an entity class for managing orders in the system.
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order extends Auditable {
    // The primary key for the Order entity. It is auto-generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    // Many-to-one relationship with the Customer entity.
    // The 'customer_id' column in the 'orders' table is a foreign key referencing
    // the Customer entity.
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // The date of the order. It is stored as a date without time.
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    // The total of the order. It is a decimal number with a precision of 10 and a
    // scale of 2.
    @Column(precision = 10, scale = 2)
    private BigDecimal orderTotal;

    // The status of the order. It is an enumerated type with the values Completed,
    // InProgress, and Cancelled.
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // One-to-many relationship with the OrderDetail entity.
    // This order can be associated with multiple order details.
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    // Enum for the order status.
    public enum OrderStatus {
        Completed, InProgress, Cancelled
    }
}