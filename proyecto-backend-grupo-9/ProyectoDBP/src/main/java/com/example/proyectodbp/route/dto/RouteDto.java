package com.example.proyectodbp.route.dto;

import com.example.proyectodbp.station.domain.Station;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RouteDto {

    @NotNull
    @Size(min = 2, max = 50)
    private String route_name;

    private List<Station> stations;


}
