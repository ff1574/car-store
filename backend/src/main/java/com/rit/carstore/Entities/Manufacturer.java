package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

// This is an entity class for managing manufacturers in the system.
@Entity
@Table(name = "manufacturers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer extends Auditable {
    // The primary key for the Manufacturer entity. It is auto-generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer manufacturerId;

    // The name of the manufacturer. It cannot be null and has a maximum length of
    // 255 characters.
    @Column(nullable = false, length = 255)
    private String manufacturerName;

    // The image of the manufacturer. It is stored as a byte array.
    @Lob
    @Column(name = "manufacturer_image")
    private byte[] manufacturerImage;

    // The country of the manufacturer. It has a maximum length of 100 characters.
    @Column(length = 100)
    private String manufacturerCountry;

    // The website of the manufacturer. It has a maximum length of 255 characters.
    @Column(length = 255)
    private String manufacturerWebsite;

    // One-to-many relationship with the Car entity.
    // This manufacturer can be associated with multiple cars.
    @OneToMany(mappedBy = "manufacturer")
    private List<Car> cars;
}