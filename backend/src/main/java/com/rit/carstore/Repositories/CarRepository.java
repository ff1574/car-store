package com.rit.carstore.Repositories;

import com.rit.carstore.Entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
}
