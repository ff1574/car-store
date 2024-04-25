package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;

// This is an entity class for managing administrators in the system.
@Entity
@Table(name = "administrators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrator extends Auditable {
    // The primary key for the Administrator entity. It is auto-generated.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer administratorId;

    // The name of the administrator. It cannot be null and has a maximum length of
    // 255 characters.
    @Column(nullable = false, length = 255)
    private String administratorName;

    // The email of the administrator. It cannot be null, has a maximum length of
    // 255 characters, and must be unique.
    @Column(nullable = false, length = 255, unique = true)
    private String administratorEmail;

    // The password of the administrator. It cannot be null and has a maximum length
    // of 255 characters.
    @Column(nullable = false, length = 255)
    private String administratorPassword;
}