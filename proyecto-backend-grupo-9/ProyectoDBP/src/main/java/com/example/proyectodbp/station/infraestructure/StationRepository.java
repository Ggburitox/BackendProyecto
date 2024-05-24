package com.example.proyectodbp.station.infraestructure;

import com.example.proyectodbp.station.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long>{
}
