package com.example.proyectodbp.station.dto;

import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class StationDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    private List<BusDto> buses;

    private List<PassengerDto> passengers;
}