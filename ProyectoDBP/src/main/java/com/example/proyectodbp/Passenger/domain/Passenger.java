package com.example.proyectodbp.Passenger.domain;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.User.domain.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Passenger extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany
    private List<Bus> buses;
}