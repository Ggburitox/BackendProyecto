package com.example.proyectodbp.route.dto;

import com.example.proyectodbp.bus.domain.Bus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class RouteBusDto {
    @NotNull
    @Size(min = 1, max = 50)
    private String route_name;

    private List<Bus> buses;
}
