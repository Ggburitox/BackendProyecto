package com.example.proyectodbp.Driver.infraestructure;

import com.example.proyectodbp.Driver.domain.Driver;
import com.example.proyectodbp.User.infraestructure.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends UserRepository<Driver> {
}