package com.example.proyectodbp.passenger.infraestructure;

import com.example.proyectodbp.passenger.domain.Passenger;
import com.example.proyectodbp.user.infraestructure.UserRepository;

import java.util.Optional;

public interface PassengerRepository extends UserRepository<Passenger> {
    Optional<Passenger> FindByEmail(String email);
}
