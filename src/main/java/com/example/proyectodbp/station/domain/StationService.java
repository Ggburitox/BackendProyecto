package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.passenger.domain.Passenger;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.auth.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {
    private final StationRepository stationRepository;
    private final AuthorizationUtils authorizationUtils;
    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final RouteRepository routeRepository;

    public StationService(StationRepository stationRepository, AuthorizationUtils authorizationUtils, PassengerRepository passengerRepository, RouteRepository routeRepository) {
        this.stationRepository = stationRepository;
        this.authorizationUtils = authorizationUtils;
        this.passengerRepository = passengerRepository;
        this.routeRepository = routeRepository;
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

    public List<StationDto> getStations() {
        return stationRepository.findAll().stream()
                .map(station -> modelMapper.map(station, StationDto.class))
                .collect(Collectors.toList());
    }

    public void deleteStation(Long id){
        Station station = stationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));
        for(Passenger passenger : station.getPassengers()){
            passenger.setStation(null);
            passengerRepository.save(passenger);
        }
        stationRepository.delete(station);
    }

    public void updateStation(Long id, StationDto stationDto) {

        Station stationToUpdate = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        stationToUpdate.setName(stationDto.getName());
        stationRepository.save(stationToUpdate);
    }

    public void addPassenger(Long id, PassengerDto passengerDto) {
        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        Passenger passenger = passengerRepository
                .findByEmail(passengerDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("This passenger does not exist"));

        if (!station.getPassengers().contains(passenger)) {
            station.addPassenger(passenger);
            passenger.setStation(station);
            stationRepository.save(station);
            passengerRepository.save(passenger);
        }
        else {
            throw new UnauthorizedOperationException("This passenger is already in the station's passenger list");
        }
    }

    public List<RouteDto> getCurrentStationRoutes() {
        String userEmail = authorizationUtils.getCurrentUserEmail();
        Passenger passenger = passengerRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("This passenger does not exist"));

        if(passenger.getStation() == null){
            throw new ResourceNotFoundException("This passenger is not in any station");
        }
        Station station = passenger.getStation();

        return routeRepository.findRoutesByStation(station).stream()
                .map(route -> modelMapper.map(route, RouteDto.class))
                .collect(Collectors.toList());
    }

    public List<BusDto> getCurrentStationBuses() {
        String userEmail = authorizationUtils.getCurrentUserEmail();
        Passenger passenger = passengerRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("This passenger does not exist"));

        if(passenger.getStation() == null){
            throw new ResourceNotFoundException("This passenger is not in any station");
        }
        Station station = passenger.getStation();
        List<Route> routes = station.getRoutes();

        return routes.stream()
                .flatMap(route -> route.getBuses().stream())
                .map(bus -> modelMapper.map(bus, BusDto.class))
                .collect(Collectors.toList());
    }

    public void removePassenger(Long id, PassengerDto passengerDto) {
        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        Passenger passenger = passengerRepository
                .findByEmail(passengerDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("This passenger does not exist"));

        if (station.getPassengers().contains(passenger)) {
            station.removePassenger(passenger);
            passenger.setStation(null);
            stationRepository.save(station);
            passengerRepository.save(passenger);
        }
        else {
            throw new UnauthorizedOperationException("This passenger is not in the station's passenger list");
        }
    }
}