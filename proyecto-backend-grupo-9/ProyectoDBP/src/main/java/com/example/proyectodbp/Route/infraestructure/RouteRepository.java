package com.example.proyectodbp.Route.infraestructure;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> addBus (Bus bus);
    List<Route> removeBus (Bus bus);
}
