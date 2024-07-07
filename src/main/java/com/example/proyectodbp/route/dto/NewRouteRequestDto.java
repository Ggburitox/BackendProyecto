package com.example.proyectodbp.route.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewRouteRequestDto {
    @Size(min = 1, max = 50)
    @NotNull
    private String name;
}