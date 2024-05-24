package com.example.proyectodbp.bus.domain;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.driver.domain.Driver;
import jakarta.persistence.*;
import lombok.Data;

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


