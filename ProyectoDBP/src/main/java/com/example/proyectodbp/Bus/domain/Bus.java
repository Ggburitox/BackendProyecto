package com.example.proyectodbp.Bus.domain;
import com.example.proyectodbp.Route.domain.Route;
import com.example.proyectodbp.Station.domain.Station;
import com.example.proyectodbp.Driver.domain.Driver;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Bus {
    @Id
    private long id;
    @Column(nullable = false)
    private String placa;
    @OneToOne
    @JoinColumn(name = "driver_id",nullable = false)
    private Driver driver;
    @ManyToOne
    @JoinColumn(name = "ruta actual",nullable = false)
    private Route route_act;
}

