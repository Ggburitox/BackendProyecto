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
    private String firstName;

    @NotNull
    @Email
    private String email;

}
