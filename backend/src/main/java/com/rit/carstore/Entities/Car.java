package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.math.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

// This is an entity class for managing cars in the system.
@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car extends Auditable {
    // The primary key for the Car entity. It is auto-generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    // Many-to-one relationship with the Manufacturer entity.
    // The 'manufacturer_id' column in the 'cars' table is a foreign key referencing
    // the Manufacturer entity.
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    // The model of the car. It has a maximum length of 255 characters.
    @Column(name = "car_model", length = 255)
    private String carModel;

    // The year of the car.
    @Column(name = "car_year")
    private Integer carYear;

    // The mileage of the car.
    @Column(name = "car_mileage")
    private Integer carMileage;

    // The price of the car. It is a decimal number with a precision of 10 and a
    // scale of 2.
    @Column(name = "car_price", precision = 10, scale = 2)
    private BigDecimal carPrice;

    // The color of the car. It has a maximum length of 50 characters.
    @Column(name = "car_color", length = 50)
    private String carColor;

    // The engine of the car. It has a maximum length of 100 characters.
    @Column(name = "car_engine", length = 100)
    private String carEngine;

    // The image of the car. It is stored as a byte array.
    @Lob
    @Column(name = "car_image")
    private byte[] carImage;

    // The stock quantity of the car.
    private Integer carStockQuantity;

    // One-to-many relationship with the OrderDetail entity.
    // This car can be associated with multiple order details.
    @OneToMany(mappedBy = "car")
    private List<OrderDetail> orderDetails;

    // Custom getter for Manufacturer's Name to avoid recursion and only show name
    @JsonProperty("manufacturerName")
    public String getManufacturerName() {
        return manufacturer != null ? manufacturer.getManufacturerName() : null;
    }
}