package com.example.proyectodbp.route.dto;

import com.example.proyectodbp.station.dto.StationDto;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RouteDto {
    @Size(min = 5, max = 50)
    @NotNull
    private String name;

    private List<StationDto> stations;
}