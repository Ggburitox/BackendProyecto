package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.dto.NewStationRequestDto;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {
    private final StationRepository stationRepository;
    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StationService(StationRepository stationRepository, RouteRepository routeRepository) {
        this.stationRepository = stationRepository;
        this.routeRepository = routeRepository;
        this.modelMapper = new ModelMapper();
    }

    public String createStation(NewStationRequestDto stationDto) {
        if (stationRepository.findByName(stationDto.getName()).isPresent()) {
            throw new ResourceNotFoundException("This station already exists");
        }

        Station station = modelMapper.map(stationDto, Station.class);
        stationRepository.save(station);
        return "/station/" + station.getId();
    }

    public StationDto getStationInfo(Long id) {
        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        return modelMapper.map(station, StationDto.class);
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    public void updateStation(Long id, StationDto stationDto) {
        Station stationToUpdate = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));
        stationToUpdate.setName(stationDto.getName());
        stationToUpdate.setRoutes(stationDto.getRoutes());
        stationRepository.save(stationToUpdate);
    }

    public void addRoute(Long id, String routeName) {
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
    }
}
