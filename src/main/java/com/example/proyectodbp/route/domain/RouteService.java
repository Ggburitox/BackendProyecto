package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.events.HelloEmailEvent;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.route.dto.NewRouteRequestDto;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.auth.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RouteService{
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper = new ModelMapper();

    private final ApplicationEventPublisher applicationEventPublisher;

    public RouteService(RouteRepository routeRepository, StationRepository stationRepository, AuthorizationUtils authorizationUtils, ApplicationEventPublisher applicationEventPublisher) {
        this.routeRepository = routeRepository;
        this.stationRepository = stationRepository;
        this.authorizationUtils = authorizationUtils;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public String createRoute(NewRouteRequestDto routeDto) {
        if (routeRepository.findByName(routeDto.getName()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This route already exists");
        }

        Route route = modelMapper.map(routeDto, Route.class);
        String message = "Route created successfully";
        applicationEventPublisher.publishEvent(new HelloEmailEvent(authorizationUtils.getCurrentUserEmail(), message));
        return "/routes/" + route.getId();
    }

    public RouteDto getRouteInfo(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Route route = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        return modelMapper.map(route, RouteDto.class);
    }

    public void deleteRoute(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        routeRepository.deleteById(id);
    }

    public void updateRoute(Long id, RouteDto routeDto) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Route routeToUpdate = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));
        routeToUpdate.setName(routeDto.getName());
        routeToUpdate.setStations(routeDto.getStations());
        routeRepository.save(routeToUpdate);
    }

    public void addStation(Long id, String stationName) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");
  
        Route route = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));
        Station station = stationRepository
                .findByName(stationName)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));
        route.getStations().add(station);
        station.getRoutes().add(route);
        routeRepository.save(route);
        stationRepository.save(station);
        String message = "Station added successfully";
        applicationEventPublisher.publishEvent(new HelloEmailEvent(authorizationUtils.getCurrentUserEmail(), message));
    }
}