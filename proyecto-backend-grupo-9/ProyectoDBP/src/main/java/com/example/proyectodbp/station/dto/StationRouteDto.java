package com.example.proyectodbp.station.dto;

import com.example.proyectodbp.route.domain.Route;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class StationRouteDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String station_name;

    private Set<Route> routes;
}
