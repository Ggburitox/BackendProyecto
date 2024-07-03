package com.example.proyectodbp.bus.dto;

import com.example.proyectodbp.driver.domain.Driver;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.station.domain.Station;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BusDto {
    @Size(min = 7, max = 7)
    @NotNull
    private String plate;

    private Driver driver;

    private Route route;

    private Station station;
}
