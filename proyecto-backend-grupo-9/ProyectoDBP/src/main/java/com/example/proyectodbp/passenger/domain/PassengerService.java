package com.example.proyectodbp.passenger.domain;

import com.example.proyectodbp.driver.domain.Driver;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public String createPassenger(PassengerDto passenger) {
        if (passengerRepository.findByEmail(passenger.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This driver already exists");
        }
        Passenger passengerToCreate = new Passenger();
        passengerToCreate.setFirstName(passenger.getFirstName());
        passengerToCreate.setLastName(passenger.getLastName());
        passengerToCreate.setEmail(passenger.getEmail());
        passengerToCreate.setDni(passenger.getDni());
        passengerRepository.save(passengerToCreate);
        return "/passenger/"+passengerToCreate.getId();
    }

    public PassengerDto getPassenger(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The passenger does not exist"));

        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setFirstName(passenger.getFirstName());
        passengerDto.setLastName(passenger.getLastName());
        passengerDto.setEmail(passenger.getEmail());
        passengerDto.setDni(passenger.getDni());
        return passengerDto;
    }

    public void deletePassenger(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The passenger does not exist"));
        passengerRepository.delete(passenger);
    }

    public void updatePassenger(Long id, PassengerDto passengerDto) {
        Passenger passengerToUpdate = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The passenger does not exist"));
        passengerToUpdate.setFirstName(passengerDto.getFirstName());
        passengerToUpdate.setLastName(passengerDto.getLastName());
        passengerToUpdate.setEmail(passengerDto.getEmail());
        passengerToUpdate.setDni(passengerDto.getDni());
        passengerRepository.save(passengerToUpdate);
    }
}