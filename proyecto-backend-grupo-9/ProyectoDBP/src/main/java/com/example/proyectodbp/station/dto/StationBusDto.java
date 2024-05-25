package com.example.proyectodbp.station.dto;

import com.example.proyectodbp.bus.domain.Bus;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class StationBusDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String station_name;

    private Set<Bus> buses;
}
