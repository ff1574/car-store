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

    @Column(length = 50)
    private String customerPhone;

    @Lob
    private String customerAddress;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
