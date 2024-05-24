package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.station.domain.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Route {
    @Id
    private Long id;

    @Column(nullable = false)
    private String route_name;


//    @JoinTable(
//            name = "route_station",
//            joinColumns = @JoinColumn(name = "route_id"),
//            inverseJoinColumns = @JoinColumn(name = "station_id")
//    )
    @ManyToMany
    private List<Station> stations;
}

