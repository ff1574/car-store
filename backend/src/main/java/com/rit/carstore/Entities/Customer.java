package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(nullable = false, length = 255)
    private String customerName;

    @Column(nullable = false, length = 255, unique = true)
    private String customerEmail;

    @Column(nullable = false, length = 255)
    private String customerPassword;

    @Column(length = 50)
    private String customerPhone;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
