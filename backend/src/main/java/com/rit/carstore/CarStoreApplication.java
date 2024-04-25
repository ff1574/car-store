package com.rit.carstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// This is the main application class for the CarStore application.
// The @SpringBootApplication annotation indicates that this is a Spring Boot application.
@SpringBootApplication
@EnableJpaAuditing
public class CarStoreApplication {

	// The main method is the entry point for the application.
	// It uses SpringApplication.run to launch the application.
	public static void main(String[] args) {
		SpringApplication.run(CarStoreApplication.class, args);
	}
}