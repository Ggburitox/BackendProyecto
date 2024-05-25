package com.example.proyectodbp.driver.dto;

import com.example.proyectodbp.bus.domain.Bus;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class DriverDto {
    @Size(min = 2, max = 50)
    @NotNull
    private String firstName;

    @Size(min = 2, max = 50)
    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    @Size(min = 8, max = 8)
    @NotNull
    private String dni;

    @NotNull
    private Bus bus;
}
