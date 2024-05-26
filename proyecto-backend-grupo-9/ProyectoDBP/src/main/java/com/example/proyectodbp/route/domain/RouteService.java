package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.route.dto.NewRouteRequestDto;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService{
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RouteService(RouteRepository routeRepository, StationRepository stationRepository) {
        this.routeRepository = routeRepository;
        this.stationRepository = stationRepository;
        this.modelMapper = new ModelMapper();
    }

    public String createRoute(NewRouteRequestDto routeDto) {
        if (routeRepository.findByName(routeDto.getName()).isPresent()) {
            throw new ResourceNotFoundException("This route already exists");
        }
        Route route = modelMapper.map(routeDto, Route.class);
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
        Route routeToUpdate = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));
        routeToUpdate.setName(routeDto.getName());
        routeToUpdate.setStations(routeDto.getStations());
        routeRepository.save(routeToUpdate);
    }

    public void addStation(Long id, String stationName) {
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
    }
}