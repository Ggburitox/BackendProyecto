package com.example.proyectodbp.Route.domain;

import com.example.proyectodbp.Route.infraestructure.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService{

    @Autowired
    private RouteRepository routeRepository;

    public void createRoute(Route newRoute) {
         routeRepository.save(newRoute);
    }

    public Route getRoute(Long id) {
        return routeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }

    public void deleteRoute(Long id) {
        routeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        routeRepository.deleteById(id);
    }

    public Route updateRoute(Long id, Route route) {
        Route RouteToUpdate = routeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        RouteToUpdate.setStations(route.getStations());
        RouteToUpdate.setRoute_name(route.getRoute_name());
        return RouteToUpdate;
   }

}
