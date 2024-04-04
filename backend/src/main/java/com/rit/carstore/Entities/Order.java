package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal orderTotal;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    public enum OrderStatus {
        Completed, InProgress, Cancelled
    }
}
