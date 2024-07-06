package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.passenger.domain.Passenger;
import com.example.proyectodbp.passenger.dto.NewPassengerRequestDto;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.dto.NewStationRequestDto;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.auth.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class StationService {
    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;
    private final AuthorizationUtils authorizationUtils;
    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final ApplicationEventPublisher applicationEventPublisher;

    public StationService(StationRepository stationRepository, RouteRepository routeRepository, AuthorizationUtils authorizationUtils, ApplicationEventPublisher applicationEventPublisher, PassengerRepository passengerRepository) {
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
        this.authorizationUtils = authorizationUtils;
        this.applicationEventPublisher = applicationEventPublisher;
        this.passengerRepository = passengerRepository;

    }

    public String createStation(StationDto newStation) {
        if (stationRepository.findByName(newStation.getName()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This station already exists");
        }

        Station station = modelMapper.map(newStation, Station.class);
        stationRepository.save(station);
        return "/station/" + station.getId();
    }

    public StationDto getStationInfo(Long id) {
        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        return modelMapper.map(station, StationDto.class);
    }

    public void deleteStation(Long id){
        Station station = stationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));
        for(Passenger passenger : station.getPassengers()){
            passenger.setStation(null);
            passengerRepository.save(passenger);
        }
        stationRepository.delete(station);
    }

    public void updateStation(Long id, NewStationRequestDto stationDto) {

        Station stationToUpdate = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        stationToUpdate.setName(stationDto.getName());
        stationRepository.save(stationToUpdate);
    }

    public void addPassenger(Long id, NewPassengerRequestDto passengerDto) {
        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        Passenger passenger = passengerRepository
                .findByEmail(passengerDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("This passenger does not exist"));

        // Check if the passenger is already in the station's passenger list
        if (!station.getPassengers().contains(passenger)) {
            // If the passenger is not in the station's passenger list, add it
            station.addPassenger(passenger);
            passenger.setStation(station);
            stationRepository.save(station);
            passengerRepository.save(passenger);
        }
        else {
            throw new UnauthorizedOperationException("This passenger is already in the station's passenger list");
        }
    }
}
