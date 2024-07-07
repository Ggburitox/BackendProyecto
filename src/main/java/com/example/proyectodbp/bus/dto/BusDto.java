package com.example.proyectodbp.bus.dto;

import com.example.proyectodbp.route.dto.RouteDto;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BusDto {
    @Size(min = 7, max = 7)
    @NotNull
    private String plate;

    private RouteDto route;
}