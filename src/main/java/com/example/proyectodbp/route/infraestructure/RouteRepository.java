package com.example.proyectodbp.route.infraestructure;

import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.station.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByName(String routeName);

    @Query("SELECT r FROM Route r JOIN r.stations s WHERE s = :station")
    List<Route> findRoutesByStation(@Param("station") Station station);
}