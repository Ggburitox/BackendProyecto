package com.example.proyectodbp.station.dto;

import com.example.proyectodbp.route.domain.Route;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class StationDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotEmpty
    private List<Route> routes;
}