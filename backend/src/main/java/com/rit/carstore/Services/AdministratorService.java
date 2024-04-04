package com.rit.carstore.Services;

import com.rit.carstore.Entities.Administrator;
import com.rit.carstore.Repositories.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministratorService {

    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    public List<Administrator> findAllAdministrators() {
        return administratorRepository.findAll();
    }

    public Optional<Administrator> findAdministratorById(Integer id) {
        return administratorRepository.findById(id);
    }

    public Administrator saveAdministrator(Administrator administrator) {
        return administratorRepository.save(administrator);
    }

    public void deleteAdministrator(Integer id) {
        administratorRepository.deleteById(id);
    }
}