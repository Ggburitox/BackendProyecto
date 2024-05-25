package com.example.proyectodbp.station.dto;

import com.example.proyectodbp.passenger.domain.Passenger;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class StationPassengerDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String station_name;

    private List<Passenger> passengers;
}
