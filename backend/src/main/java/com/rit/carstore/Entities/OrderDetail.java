package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderDetailId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "car_id")
    private Car car;

    private Integer carQuantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal carPricePerUnit;
}
