package com.example.proyectodbp.route.infraestructure;

import com.example.proyectodbp.route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByName(String routeName);
}

