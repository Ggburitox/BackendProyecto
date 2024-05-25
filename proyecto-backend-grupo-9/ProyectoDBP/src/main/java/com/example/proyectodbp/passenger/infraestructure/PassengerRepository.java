package com.example.proyectodbp.passenger.infraestructure;

import com.example.proyectodbp.passenger.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByEmail(String email);
}
