package com.rit.carstore.Entities;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

// This is an entity class for managing customer addresses in the system.
@Entity
@Table(name = "customer_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddress extends Auditable {
    // The primary key for the CustomerAddress entity. It is auto-generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerAddressId;

    // Many-to-one relationship with the Customer entity.
    // The 'customer_id' column in the 'customer_addresses' table is a foreign key
    // referencing the Customer entity.
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    // The first line of the customer's address. It cannot be null and has a maximum
    // length of 255 characters.
    @Column(nullable = false, length = 255)
    private String customerAddressLine1;

    // The second line of the customer's address. It has a maximum length of 255
    // characters.
    @Column(length = 255)
    private String customerAddressLine2;

    // The city of the customer's address. It cannot be null and has a maximum
    // length of 100 characters.
    @Column(nullable = false, length = 100)
    private String customerCity;

    // The state or province of the customer's address. It has a maximum length of
    // 100 characters.
    @Column(length = 100)
    private String customerStateOrProvince;

    // The postal code of the customer's address. It has a maximum length of 20
    // characters.
    @Column(length = 20)
    private String customerPostalCode;

    // The country of the customer's address. It cannot be null and has a maximum
    // length of 100 characters.
    @Column(nullable = false, length = 100)
    private String customerCountry;
}