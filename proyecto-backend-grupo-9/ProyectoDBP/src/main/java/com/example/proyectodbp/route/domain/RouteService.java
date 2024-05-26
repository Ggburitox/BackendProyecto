package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizeOperationException;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import com.example.proyectodbp.utils.AuthorizationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RouteService{
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private UserRepository<User> userRepository;
    @Autowired
    private AuthorizationUtils authorizationUtils;

    public String createRoute(RouteDto routeDto) {
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

        Route route = new Route();
        route.setName(routeDto.getName());
        route.setStations(routeDto.getStations());
        routeRepository.save(route);
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

        RouteDto routeDto = new RouteDto();
        routeDto.setName(route.getName());
        routeDto.setStations(route.getStations());
        return routeDto;
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

    public StationDto addStation(Long id, String stationName) {
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

        StationDto stationDto = new StationDto();
        stationDto.setName(station.getName());
        stationDto.setRoutes(station.getRoutes());
        return stationDto;
    }
}