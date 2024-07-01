package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.events.HelloEmailEvent;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.dto.NewStationRequestDto;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class StationService {
    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper = new ModelMapper();
    private final ApplicationEventPublisher applicationEventPublisher;

    public StationService(StationRepository stationRepository, RouteRepository routeRepository, AuthorizationUtils authorizationUtils, ApplicationEventPublisher applicationEventPublisher) {
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
        this.authorizationUtils = authorizationUtils;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public String createStation(NewStationRequestDto newStation) {
        if (stationRepository.findByName(newStation.getName()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This station already exists");
        }

        Station station = modelMapper.map(newStation, Station.class);
        stationRepository.save(station);
        String message = "Station created";
        applicationEventPublisher.publishEvent(new HelloEmailEvent(authorizationUtils.getCurrentUserEmail(), message));
        return "/station/" + station.getId();
    }

    public StationDto getStationInfo(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        return modelMapper.map(station, StationDto.class);
    }

    public void deleteStation(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        stationRepository.deleteById(id);
    }

    public void updateStation(Long id, StationDto stationDto) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Station stationToUpdate = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        stationToUpdate.setName(stationDto.getName());
        stationToUpdate.setRoutes(stationDto.getRoutes());
        stationRepository.save(stationToUpdate);
        String message = "Station updated";
        applicationEventPublisher.publishEvent(new HelloEmailEvent(authorizationUtils.getCurrentUserEmail(), message));
    }

    public void addRoute(Long id, String routeName) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));
        Route route = routeRepository
                .findByName(routeName)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));
        station.getRoutes().add(route);
        route.getStations().add(station);
        stationRepository.save(station);
        routeRepository.save(route);
        String message = "Route added to station";
        applicationEventPublisher.publishEvent(new HelloEmailEvent(authorizationUtils.getCurrentUserEmail(), message));
    }
}