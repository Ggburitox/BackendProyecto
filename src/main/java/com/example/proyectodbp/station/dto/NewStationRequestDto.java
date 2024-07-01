package com.example.proyectodbp.station.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewStationRequestDto {
    @NotNull
    @Size(min = 2, max = 50)
    private String name;
}