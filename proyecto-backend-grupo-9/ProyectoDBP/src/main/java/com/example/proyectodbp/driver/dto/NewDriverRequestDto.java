package com.example.proyectodbp.driver.dto;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.user.domain.Role;
import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewDriverRequestDto {
    @NotNull
    private Role role = Role.DRIVER;

    @Size(min = 2, max = 50)
    @NotNull
    private String firstName;

    @Size(min = 2, max = 50)
    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    @Size(min = 5, max = 20)
    @NotNull
    private String password;

    @Size(min = 8, max = 8)
    @NotNull
    private String dni;

    @NotNull
    private Bus bus;
}