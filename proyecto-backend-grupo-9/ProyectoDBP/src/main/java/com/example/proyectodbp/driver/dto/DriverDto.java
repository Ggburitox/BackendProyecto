package com.example.proyectodbp.driver.dto;

import jakarta.persistence.Column;
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
    private String email;

    @Size(min = 9, max = 9)
    @NotNull
    private String phoneNumber;

    @Size(min = 8, max = 8)
    @NotNull
    private String dni;
}
