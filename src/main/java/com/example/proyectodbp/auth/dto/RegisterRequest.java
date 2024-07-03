package com.example.proyectodbp.auth.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;

    private Boolean isDriver=false;
}
