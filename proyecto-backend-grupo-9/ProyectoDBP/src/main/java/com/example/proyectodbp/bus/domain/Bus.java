package com.example.proyectodbp.bus.domain;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.driver.domain.Driver;
import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Size;

@Entity
@Data
public class Bus {
    @Id
    private Long id;

    @Column(nullable = false)
    @Size(min = 6, max = 6)
    private String plate;

    @OneToOne
    private Driver driver;

    @ManyToOne
    private Route route;

    @ManyToOne
    private Station station;
}


