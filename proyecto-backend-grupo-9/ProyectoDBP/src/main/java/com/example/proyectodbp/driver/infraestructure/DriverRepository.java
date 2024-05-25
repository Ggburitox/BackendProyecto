package com.example.proyectodbp.driver.infraestructure;

import com.example.proyectodbp.driver.domain.Driver;
import com.example.proyectodbp.user.infraestructure.UserRepository;

import java.util.Optional;

public interface DriverRepository extends UserRepository<Driver> {
    Optional<Driver> findByEmail(String email);
}