package com.example.proyectodbp.bus.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewBusRequestDto {
    @Size(min = 6, max = 6)
    @NotNull
    private String plate;
}
