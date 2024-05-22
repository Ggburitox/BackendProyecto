package com.example.proyectodbp.Station.infraestructure;

import com.example.proyectodbp.Station.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long>{
}
