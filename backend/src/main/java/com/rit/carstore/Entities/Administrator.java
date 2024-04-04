package com.rit.carstore.Entities;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "administrators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer administratorId;

    @Column(nullable = false, length = 255)
    private String administratorName;

    @Column(nullable = false, length = 255, unique = true)
    private String administratorEmail;

    @Column(nullable = false, length = 255)
    private String administratorPassword;
}
