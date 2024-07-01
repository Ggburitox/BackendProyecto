package com.example.proyectodbp.passenger.domain;

import com.example.proyectodbp.events.HelloEmailEvent;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final StationRepository stationRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PassengerService(PassengerRepository passengerRepository, StationRepository stationRepository, AuthorizationUtils authorizationUtils, ModelMapper modelMapper, ApplicationEventPublisher applicationEventPublisher) {
        this.passengerRepository = passengerRepository;
        this.stationRepository = stationRepository;
        this.authorizationUtils = authorizationUtils;
        this.applicationEventPublisher = applicationEventPublisher;
        this.modelMapper = modelMapper;
    }

    public PassengerDto getPassengerInfo(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The passenger does not exist"));

        return modelMapper.map(passenger, PassengerDto.class);
    }

    public void deletePassenger(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        passengerRepository.deleteById(id);
    }

    public void updatePassenger(Long id, PassengerDto passengerDto) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Passenger passengerToUpdate = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The passenger does not exist"));
        passengerToUpdate.setFirstName(passengerDto.getFirstName());
        passengerToUpdate.setLastName(passengerDto.getLastName());
        passengerToUpdate.setEmail(passengerDto.getEmail());
        passengerRepository.save(passengerToUpdate);
    }

    public void updatePassengerStation(Long id, String stationName) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");
  
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
        String message = "The passenger station has been updated to " + stationName;
        applicationEventPublisher.publishEvent(new HelloEmailEvent(passenger.getEmail(), message));
    }
}