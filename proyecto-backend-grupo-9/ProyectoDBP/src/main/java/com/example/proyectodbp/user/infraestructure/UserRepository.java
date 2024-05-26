package com.example.proyectodbp.user.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyectodbp.user.domain.User;

import java.util.Optional;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
    Optional<T> findByEmail(String email); // Specify the type parameter
}
