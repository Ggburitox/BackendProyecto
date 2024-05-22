package com.example.proyectodbp.Route.domain;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Station.domain.*;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Route {
    @Id
    private long id;
    private String route_name;
    @Column(nullable = false)
    private Status status;
    @OneToMany
    private List<Station> stations;

    public Route(){};


}
