package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.route.dto.NewRouteRequestDto;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService{
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public RouteService(RouteRepository routeRepository, StationRepository stationRepository) {
        this.routeRepository = routeRepository;
        this.stationRepository = stationRepository;
    }

    public String createRoute(NewRouteRequestDto routeDto) {
        if (routeRepository.findByName(routeDto.getName()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This route already exists");
        }

        Route route = modelMapper.map(routeDto, Route.class);
        routeRepository.save(route);
        return "/routes/" + route.getId();
    }

    public RouteDto getRouteInfo(Long id) {
        Route route = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        return modelMapper.map(route, RouteDto.class);
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

    public List<RouteDto> getRoutes() {
        return routeRepository.findAll()
                .stream()
                .map(route -> modelMapper.map(route, RouteDto.class))
                .collect(Collectors.toList());
    }

    public void addStation(String name, StationDto stationDto) {
        Route route = routeRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        Station station = stationRepository
                .findByName(stationDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        route.addStation(station);
        station.addRoute(route);
        routeRepository.save(route);
        stationRepository.save(station);
    }

    public void deleteRoute(String name) {
        Route route = routeRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        routeRepository.delete(route);
    }
}