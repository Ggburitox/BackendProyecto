package com.example.proyectodbp.Station.domain;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Route.domain.Route;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;


@Entity
@Data
public class Station{
    @Id
    private Long id;
    @Column(nullable = false)
    private String station_name;
    @ManyToMany(mappedBy = "Route")
    private List<Route> Routes;
    @OneToMany(mappedBy = "Bus")
    private List<Bus> Buses;

    public Station() {}
}
