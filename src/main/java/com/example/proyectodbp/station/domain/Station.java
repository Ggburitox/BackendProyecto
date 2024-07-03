package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.passenger.domain.Passenger;
import com.example.proyectodbp.route.domain.Route;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "stations")
    private List<Route> routes;

    @OneToMany(mappedBy = "station")
    private List<Bus> buses;

    @OneToMany(mappedBy = "station")
    private List<Passenger> passengers;

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
    }

    public void addRoute(Route route) {
        routes.add(route);
    }
}