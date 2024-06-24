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


@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Station {
    @Id
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 50)
    private String name;

    @ManyToMany(mappedBy = "routes")
    private List<Route> routes;

    @OneToMany(mappedBy = "buses")
    private List<Bus> buses;

    @OneToMany(mappedBy = "passenger")
    private List<Passenger> passengers;
}