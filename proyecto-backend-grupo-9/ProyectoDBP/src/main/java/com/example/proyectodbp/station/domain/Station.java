package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.passenger.domain.Passenger;
import com.example.proyectodbp.route.domain.Route;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Size;
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
    @Size(min = 2, max = 50)
    private String name;

    @ManyToMany(mappedBy = "stations")
    private Set<Route> routes;

    @OneToMany(mappedBy = "station")
    private Set<Bus> buses;

    @OneToMany
    private List<Passenger> passengers;
}