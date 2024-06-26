package com.example.proyectodbp.passenger.domain;

import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Passenger extends User {

    @ManyToOne
    private Station station;
}
