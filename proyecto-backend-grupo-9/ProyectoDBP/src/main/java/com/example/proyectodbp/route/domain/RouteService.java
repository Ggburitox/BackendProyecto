package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizeOperationException;
import com.example.proyectodbp.route.dto.NewRouteRequestDto;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
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
  
    @Autowired
    private UserRepository<User> userRepository;
    @Autowired
    private AuthorizationUtils authorizationUtils;

    public String createRoute(NewRouteRequestDto routeDto) {
        // Aquí obtienes el identificador del usuario actual (correo electrónico) utilizando Spring Security
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
  
        if (routeRepository.findByName(routeDto.getName()).isPresent()) {
            throw new ResourceNotFoundException("This route already exists");
        }
        Route route = modelMapper.map(routeDto, Route.class);
        return "/routes/" + route.getId();
    }

    public RouteDto getRouteInfo(Long id) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }

        Route route = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        return modelMapper.map(route, RouteDto.class);
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    public void updateRoute(Long id, RouteDto routeDto) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }

        Route routeToUpdate = routeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));
        routeToUpdate.setName(routeDto.getName());
        routeToUpdate.setStations(routeDto.getStations());
        routeRepository.save(routeToUpdate);
    }

    public void addStation(Long id, String stationName) {
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }
  
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