package com.example.proyectodbp.route.dto;

import com.example.proyectodbp.station.domain.Station;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RouteStationDto {
    @NotNull
    @Size(min = 1, max = 50)
    private String route_name;

    private List<Station> stations;
}
