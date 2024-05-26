package com.example.proyectodbp.bus.dto;

import com.example.proyectodbp.driver.domain.Driver;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.station.domain.Station;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewBusRequestDto {
    @Size(min = 6, max = 6)
    @NotNull
    private String plate;

    @NotNull
    private Driver driver;

    @NotNull
    private Route route;

    @NotNull
    private Station station;
}
