package com.example.proyectodbp.driver.dto;

import com.example.proyectodbp.bus.dto.BusDto;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DriverDto {
    @NotNull
    private Long id;

    @Size(min = 2, max = 50)
    @NotNull
    private String firstName;

    @Size(min = 2, max = 50)
    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    private BusDto bus;
}
