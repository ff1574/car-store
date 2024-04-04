package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.math.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @Column(nullable = false, length = 255)
    private String carModel;

    @Column(nullable = false)
    private Integer carYear;

    @Column(precision = 10, scale = 2)
    private BigDecimal carPrice;

    @Column(length = 50)
    private String carColor;

    @Column(length = 100)
    private String carEngine;

    private Integer carStockQuantity;

    @OneToMany(mappedBy = "car")
    private List<OrderDetail> orderDetails;

    // Custom getter for Manufacturer's Name to avoid recursion and only show name
    @JsonProperty("manufacturerName")
    public String getManufacturerName() {
        return manufacturer != null ? manufacturer.getManufacturerName() : null;
    }
}
