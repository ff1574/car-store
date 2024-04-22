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
@ToString
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(name = "car_model", length = 255)
    private String carModel;

    @Column(name = "car_year")
    private Integer carYear;

    @Column(name = "car_mileage")
    private Integer carMileage;

    @Column(name = "car_price", precision = 10, scale = 2)
    private BigDecimal carPrice;

    @Column(name = "car_color", length = 50)
    private String carColor;

    @Column(name = "car_engine", length = 100)
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
