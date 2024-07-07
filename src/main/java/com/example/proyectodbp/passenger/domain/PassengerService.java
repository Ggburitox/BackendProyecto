package com.example.proyectodbp.passenger.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.auth.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final StationRepository stationRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper = new ModelMapper();


    public PassengerService(PassengerRepository passengerRepository, StationRepository stationRepository, AuthorizationUtils authorizationUtils) {
        this.passengerRepository = passengerRepository;
        this.stationRepository = stationRepository;
        this.authorizationUtils = authorizationUtils;
    }

    public PassengerDto getPassengerInfo(Long id) {
        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("The passenger does not exist"));

        return modelMapper.map(passenger, PassengerDto.class);
    }

    public PassengerDto getPassengerOwnInfo() {
        String email = authorizationUtils.getCurrentUserEmail();
        if (email==null) {
            throw new UnauthorizedOperationException("Anonymus user not allowed to access this resource");
        }
        Passenger passenger = passengerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Passenger not found"));
        return getPassengerInfo(passenger.getId());
    }

    public void deletePassenger() {
        String email = authorizationUtils.getCurrentUserEmail();
        if (email == null) {
            throw new UnauthorizedOperationException("Anonymous user not allowed to access this resource");
        }

        Passenger passenger = passengerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The passenger does not exist"));

        passengerRepository.deleteById(passenger.getId());
    }

    public void updateCurrentPassenger(PassengerDto passengerDto) {
        String email = authorizationUtils.getCurrentUserEmail();
        if (email == null) {
            throw new UnauthorizedOperationException("Anonymous user not allowed to access this resource");
        }

        Passenger passengerToUpdate = passengerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The passenger does not exist"));

        passengerToUpdate.setFirstName(passengerDto.getFirstName());
        passengerToUpdate.setLastName(passengerDto.getLastName());
        passengerToUpdate.setEmail(passengerDto.getEmail());
        passengerRepository.save(passengerToUpdate);
    }

    public void updatePassengerStation(StationDto stationDto) {
        String email = authorizationUtils.getCurrentUserEmail();
        if (email==null) {
            throw new UnauthorizedOperationException("Anonymous user not allowed to access this resource");
        }

        Passenger passenger = passengerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The passenger does not exist"));

        Station oldStation = passenger.getStation();
        if (oldStation != null) {
            oldStation.removePassenger(passenger);
        }

        Station newStation = stationRepository
                .findByName(stationDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException("The station does not exist"));

        passenger.setStation(newStation);
        newStation.addPassenger(passenger);
        passengerRepository.save(passenger);
        stationRepository.save(newStation);
    }
}
