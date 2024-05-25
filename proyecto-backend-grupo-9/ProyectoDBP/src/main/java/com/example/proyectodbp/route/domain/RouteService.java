package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.exceptions.EntityAlreadyExists;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService{

    @Autowired
    private RouteRepository routeRepository;

    public String createRoute(RouteDto newRoute) {
        if (routeRepository.findByRoute_name(newRoute.getRoute_name()).isPresent()) {
            throw new EntityAlreadyExists("This route already exists");
        }

        Route route = new Route();
        route.setRoute_name(newRoute.getRoute_name());
        route.setStations(newRoute.getStations());
        routeRepository.save(route);

        return "/routes/" + route.getId();
    }

    public RouteDto getRoute(Long id) {
        Route route = routeRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This route does not exist"));

        RouteDto RouteToUpdate = new RouteDto();
        RouteToUpdate.setStations(route.getStations());
        RouteToUpdate.setRoute_name(route.getRoute_name());
        return RouteToUpdate;

    }

    public void deleteRoute(Long id) {
        routeRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This route does not exist"));

        routeRepository.deleteById(id);
    }

    public void updateRoute(Long id, RouteDto routeDto) {
                routeRepository.
                findById(id).
                orElseThrow(() -> new EntityAlreadyExists("This route does not exist"));

        Route routeToUpdate = new Route();
        routeToUpdate.setRoute_name(routeDto.getRoute_name());
        routeToUpdate.setStations(routeDto.getStations());
        routeRepository.save(routeToUpdate);
    }

}
