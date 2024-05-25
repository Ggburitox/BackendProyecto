package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
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
                .orElseThrow(() -> new UniqueResourceAlreadyExist("This route does not exist"));
    }

    public void deleteRoute(Long id) {
        routeRepository
                .findById(id)
                .orElseThrow(() -> new UniqueResourceAlreadyExist("This route does not exist"));

        routeRepository.deleteById(id);
    }

    public void updateRoute(Long id, Route route) {
        Route RouteToUpdate = routeRepository
                .findById(id)
                .orElseThrow(() -> new UniqueResourceAlreadyExist("This route does not exist"));

        RouteToUpdate.setStations(route.getStations());
        RouteToUpdate.setRoute_name(route.getRoute_name());
    }

}
