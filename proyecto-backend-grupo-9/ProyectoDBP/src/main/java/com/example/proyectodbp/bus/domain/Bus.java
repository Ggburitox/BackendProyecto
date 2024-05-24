package com.example.proyectodbp.bus.domain;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.driver.domain.Driver;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import javax.validation.constraints.Size;

@Entity
@Data
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Bus {
    @Id
    private Long id;

    @Column(nullable = false)
    @Size(min = 6, max = 6)
    private String placa;

    @OneToOne
    private Driver driver;

    @ManyToOne
    private Route route_act;

    @ManyToOne
    private Station station;
}


