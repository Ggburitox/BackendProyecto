package com.example.proyectodbp.bus.infraestructure;

import com.example.proyectodbp.bus.domain.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus,Long> {
}
