package com.example.proyectodbp.passenger.domain;

import com.example.proyectodbp.exceptions.EntityAlreadyExists;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public String createPassenger(PassengerDto passenger) {
        if (passengerRepository.FindByEmail(passenger.getEmail()).isPresent()) {
            throw new EntityAlreadyExists("This driver already exists");
        }
        Passenger newPassenger = new Passenger();
        newPassenger.setFirstName(passenger.getFirstName());
        newPassenger.setLastName(passenger.getLastName());
        newPassenger.setEmail(passenger.getEmail());
        newPassenger.setDni(passenger.getDni());
        passengerRepository.save(newPassenger);
        return "/driver/"+newPassenger.getId();
    }

    public PassengerDto getPassenger(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("The passenger does not exist"));

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
                .orElseThrow(() -> new EntityAlreadyExists("The passenger does not exist"));
        passengerRepository.delete(passenger);
    }

    public void updatePassenger(Long id, PassengerDto passengerDto) {
        Passenger passengerToUpdate = passengerRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("The passenger does not exist"));
        passengerToUpdate.setFirstName(passengerDto.getFirstName());
        passengerToUpdate.setLastName(passengerDto.getLastName());
        passengerToUpdate.setEmail(passengerDto.getEmail());
        passengerToUpdate.setDni(passengerDto.getDni());
        passengerRepository.save(passengerToUpdate);
    }
}