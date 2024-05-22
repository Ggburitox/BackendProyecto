package com.example.proyectodbp.Driver.domain;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.User.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class Driver extends User {

    @OneToOne
    private Bus bus;
}

