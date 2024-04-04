package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "manufacturers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer manufacturerId;

    @Column(nullable = false, length = 255)
    private String manufacturerName;

    @Column(length = 100)
    private String manufacturerCountry;

    @Column(length = 255)
    private String manufacturerWebsite;

    @OneToMany(mappedBy = "manufacturer")
    private List<Car> cars;
}
