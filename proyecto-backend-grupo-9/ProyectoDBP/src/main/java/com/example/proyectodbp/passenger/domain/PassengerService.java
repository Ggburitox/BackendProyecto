package com.example.proyectodbp.passenger.domain;

import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public void createPassenger(Passenger passenger) {
        passengerRepository.save(passenger);
    }

    public Passenger getPassenger(Long id) {
        return passengerRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("driver not found"));
    }

    public void deletePassenger(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("driver not found"));
        passengerRepository.delete(passenger);
    }

    public Passenger updatePassenger(Long id, Passenger passenger) {
        Passenger passengertoUpdate = passengerRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("driver not found"));
        passengertoUpdate.setStation(passenger.getStation());
        passengertoUpdate.setFirstName(passenger.getFirstName());
        passengertoUpdate.setLastName(passenger.getLastName());
        passengertoUpdate.setEmail(passenger.getEmail());
        passengertoUpdate.setPhoneNumber(passenger.getPhoneNumber());
        passengerRepository.save(passengertoUpdate);
        return passengertoUpdate;
    }

}
