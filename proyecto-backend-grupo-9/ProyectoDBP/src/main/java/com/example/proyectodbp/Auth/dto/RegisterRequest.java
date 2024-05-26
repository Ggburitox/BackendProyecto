package com.example.proyectodbp.Auth.dto;


import lombok.Data;
@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Boolean isDriver=false;

}
