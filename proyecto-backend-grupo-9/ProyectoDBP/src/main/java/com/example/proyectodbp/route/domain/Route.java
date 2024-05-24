package com.example.proyectodbp.route.domain;

import com.example.proyectodbp.station.domain.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @Size(min = 2, max = 50)
    private String route_name;

    @ManyToMany
    private List<Station> stations;
}

