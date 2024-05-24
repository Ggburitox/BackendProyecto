package com.example.proyectodbp.user.domain;


import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "first_name", nullable = false)
    @Size(min = 2, max = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 2, max = 50)
    private String lastName;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 5, max = 20)
    private String password;

    @Column(name = "phone_number", nullable = false)
    @Size(min = 9, max = 9)
    private String phoneNumber;

    @Column(name = "dni", nullable = false)
    @Size(min = 8, max = 8)
    private String dni;

    @Transient
    private String rolePrefix = "ROLE_";
}