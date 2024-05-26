package com.example.proyectodbp.auth.dto;


import lombok.Data;
@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Boolean isDriver=false;

}
