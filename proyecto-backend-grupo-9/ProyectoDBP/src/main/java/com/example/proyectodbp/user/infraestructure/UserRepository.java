package com.example.proyectodbp.user.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyectodbp.user.domain.User;

public interface UserRepository<T extends User> extends JpaRepository<T, Long> {
}
