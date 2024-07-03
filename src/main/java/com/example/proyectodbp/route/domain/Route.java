package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.station.domain.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 1, max = 50)
    private String name;

    @OneToMany(mappedBy = "route")
    private List<Bus> buses;

    @ManyToMany
    private List<Station> stations;

    public void addStation(Station station) {
        stations.add(station);
    }

    public void addBus(Bus bus) {
        buses.add(bus);
    }
}