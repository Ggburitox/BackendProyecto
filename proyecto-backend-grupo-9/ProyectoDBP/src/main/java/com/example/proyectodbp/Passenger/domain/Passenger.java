package com.example.proyectodbp.Passenger.domain;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Station.domain.Station;
import com.example.proyectodbp.User.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Passenger extends User {
    @ManyToOne
    private Station station;
}
