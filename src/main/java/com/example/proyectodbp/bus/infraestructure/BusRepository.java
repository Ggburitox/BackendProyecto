package com.example.proyectodbp.bus.infraestructure;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.station.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus,Long> {
    Optional<Bus> findByPlate(String plate);
    List<Bus> findAllByStation(Station station);
}
