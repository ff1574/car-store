package com.rit.carstore.Repositories;

import com.rit.carstore.Entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
}
