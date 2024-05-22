package com.example.proyectodbp.Driver.domain;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.User.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Driver extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Bus bus;
}
