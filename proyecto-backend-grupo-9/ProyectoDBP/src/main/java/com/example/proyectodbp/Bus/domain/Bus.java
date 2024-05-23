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
    private Long id;

    @Column(nullable = false)
    private String placa;

    @OneToOne
    private Driver driver;

    @ManyToOne
    private Route route_act;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;
}


