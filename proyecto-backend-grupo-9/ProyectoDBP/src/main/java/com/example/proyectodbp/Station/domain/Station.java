package com.example.proyectodbp.Station.domain;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Route.domain.Route;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.util.List;
import java.util.Set;


@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Station {
    @Id
    private Long id;

    @Column(nullable = false)
    private String station_name;

    @ManyToMany(mappedBy = "stations")
    private List<Route> routes;

    @OneToMany(mappedBy = "station")
    private Set<Bus> buses;
}

