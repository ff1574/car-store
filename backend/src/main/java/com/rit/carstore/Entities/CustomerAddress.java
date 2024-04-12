package com.rit.carstore.Entities;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerAddressId;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;

    @Column(nullable = false, length = 255)
    private String customerAddressLine1;

    @Column(length = 255)
    private String customerAddressLine2;

    @Column(nullable = false, length = 100)
    private String customerCity;

    @Column(length = 100)
    private String customerStateOrProvince;

    @Column(length = 20)
    private String customerPostalCode;

    @Column(nullable = false, length = 100)
    private String customerCountry;
}
