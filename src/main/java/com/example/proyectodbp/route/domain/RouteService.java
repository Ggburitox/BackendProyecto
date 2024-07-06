package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.events.HelloEmailEvent;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.route.dto.NewRouteRequestDto;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.dto.NewStationRequestDto;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.auth.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        routeRepository.save(route); // Save the route to the database
        return "/routes/" + route.getId();
    }

    public RouteDto getRouteInfo(Long id) {
        Route route = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        return modelMapper.map(route, RouteDto.class);
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    public void updateRoute(Long id, RouteDto routeDto) {

        Route route = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        List<Station> stations = stationRepository
                .findByNameIn(routeDto.getStations()
                .stream()
                .map(StationDto::getName)
                .collect(Collectors.toList()));

        route.setName(routeDto.getName());
        route.setStations(stations);
        routeRepository.save(route);
    }

    public void addStation(Long id, NewStationRequestDto NewStationRequestDto) {
        Route newRoute = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        Station station = stationRepository
                .findByName(NewStationRequestDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        // Check if the station is already in the new route's station list
        if (!newRoute.getStations().contains(station)) {
            // If the station is not in the new route's station list, add it
            newRoute.addStation(station);
            station.addRoute(newRoute);
            routeRepository.save(newRoute);
            stationRepository.save(station);
        }
    }

}
