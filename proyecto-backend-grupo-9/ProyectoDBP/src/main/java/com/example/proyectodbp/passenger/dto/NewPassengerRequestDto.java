package com.example.proyectodbp.passenger.dto;

import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.user.domain.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewPassengerRequestDto {
    @NotNull
    private Role role = Role.PASSENGER;

    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Size(min = 8, max = 8)
    private String dni;

    @NotNull
    private Station station;
}
