package com.example.proyectodbp.Bus.infraestructure;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BusRepository extends JpaRepository<Bus,Long> {
}
