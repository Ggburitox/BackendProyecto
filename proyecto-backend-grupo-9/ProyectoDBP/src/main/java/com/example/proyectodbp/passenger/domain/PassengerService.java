package com.example.proyectodbp.passenger.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.passenger.dto.NewPassengerRequestDto;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import com.example.proyectodbp.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final StationRepository stationRepository;
    private final UserRepository<User> userRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;

    public PassengerService(PassengerRepository passengerRepository, StationRepository stationRepository, UserRepository<User> userRepository, AuthorizationUtils authorizationUtils, ModelMapper modelMapper) {
        this.passengerRepository = passengerRepository;
        this.stationRepository = stationRepository;
        this.userRepository = userRepository;
        this.authorizationUtils = authorizationUtils;
        this.modelMapper = modelMapper;
    }
  
    public String createPassenger(NewPassengerRequestDto passengerDto) {
        // Aquí obtienes el identificador del usuario actual (correo electrónico) utilizando Spring Security
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null) {
            throw new UnauthorizedOperationException("Anonymous User not allowed to access");
        }
  
        // Verifica que el usuario actual sea un DRIVER
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  
        if(user.getRole() != Role.DRIVER) {
            throw new UnauthorizedOperationException("No estas autorizado para acceder a este recurso");
        }

        if (passengerRepository.findByEmail(passengerDto.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This driver already exists");
        }
  
        Passenger passenger = modelMapper.map(passengerDto, Passenger.class);
        passengerRepository.save(passenger);
        return "/passenger/" + passenger.getId();
    }

    public PassengerDto getPassengerInfo(Long id) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizedOperationException("No estas autorizado para acceder a este recurso");
        }

        Passenger passenger = passengerRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The passenger does not exist"));

        return modelMapper.map(passenger, PassengerDto.class);
    }

    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }

    public void updatePassenger(Long id, PassengerDto passengerDto) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizedOperationException("No estas autorizado para acceder a este recurso");
        }

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
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizedOperationException("No estas autorizado para acceder a este recurso");
        }
  
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