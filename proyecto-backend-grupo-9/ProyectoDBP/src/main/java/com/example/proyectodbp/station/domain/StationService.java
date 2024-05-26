package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizeOperationException;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.dto.NewStationRequestDto;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import com.example.proyectodbp.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    
    @Autowired
    private UserRepository<User> userRepository;
    @Autowired
    private AuthorizationUtils authorizationUtils;

    public String createStation(NewStationRequestDto stationDto) {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null) {
            throw new UnauthorizeOperationException("Anonymous User not allowed to access");
        }

        // Verifica que el usuario actual sea un DRIVER
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user.getRole() != Role.DRIVER) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }
  
        if (stationRepository.findByName(stationDto.getName()).isPresent()) {
            throw new ResourceNotFoundException("This station already exists");
        }

        Station station = modelMapper.map(stationDto, Station.class);
        stationRepository.save(station);
        return "/station/" + station.getId();
    }

    public StationDto getStationInfo(Long id) {
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }

        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        return modelMapper.map(station, StationDto.class);
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    public void updateStation(Long id, StationDto stationDto) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }

        Station stationToUpdate = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));
        stationToUpdate.setName(stationDto.getName());
        stationToUpdate.setRoutes(stationDto.getRoutes());
        stationRepository.save(stationToUpdate);
    }

    public void addRoute(Long id, String routeName) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }
  
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
