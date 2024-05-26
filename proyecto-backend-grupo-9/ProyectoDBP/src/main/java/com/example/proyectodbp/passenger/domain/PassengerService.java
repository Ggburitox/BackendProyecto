package com.example.proyectodbp.passenger.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.passenger.dto.NewPassengerRequestDto;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final StationRepository stationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository, StationRepository stationRepository) {
        this.passengerRepository = passengerRepository;
        this.stationRepository = stationRepository;
        this.modelMapper = new ModelMapper();
    }

    public String createPassenger(NewPassengerRequestDto passengerDto) {
        if (passengerRepository.findByEmail(passengerDto.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This driver already exists");
        }
        Passenger passenger = modelMapper.map(passengerDto, Passenger.class);
        passengerRepository.save(passenger);
        return "/passenger/" + passenger.getId();
    }

    public PassengerDto getPassengerInfo(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The passenger does not exist"));

        return modelMapper.map(passenger, PassengerDto.class);
    }

    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
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

    public void updatePassengerStation(Long id, String stationName) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The passenger does not exist"));
        Station station = stationRepository
                .findByName(stationName)
                .orElseThrow(() -> new ResourceNotFoundException("The station does not exist"));

        passenger.setStation(station);
        station.getPassengers().add(passenger);
        passengerRepository.save(passenger);
        stationRepository.save(station);
    }
}