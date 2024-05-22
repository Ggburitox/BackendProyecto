package com.example.proyectodbp.User.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyectodbp.User.domain.User;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
}
