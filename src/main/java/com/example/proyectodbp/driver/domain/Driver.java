package com.example.proyectodbp.driver.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Driver extends User {
    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;
}