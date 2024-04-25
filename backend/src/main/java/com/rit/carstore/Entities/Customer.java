package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

// This is an entity class for managing customers in the system.
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Auditable {
    // The primary key for the Customer entity. It is auto-generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    // The name of the customer. It cannot be null and has a maximum length of 255
    // characters.
    @Column(nullable = false, length = 255)
    private String customerName;

    // The email of the customer. It cannot be null, has a maximum length of 255
    // characters, and must be unique.
    @Column(nullable = false, length = 255, unique = true)
    private String customerEmail;

    // The password of the customer. It cannot be null and has a maximum length of
    // 255 characters.
    @Column(nullable = false, length = 255)
    private String customerPassword;

    // The phone number of the customer. It has a maximum length of 50 characters.
    @Column(length = 50)
    private String customerPhone;

    // One-to-many relationship with the Order entity.
    // This customer can be associated with multiple orders.
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}