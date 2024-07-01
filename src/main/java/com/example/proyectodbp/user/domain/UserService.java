package com.example.proyectodbp.user.domain;

import com.example.proyectodbp.driver.infraestructure.DriverRepository;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository<User> userRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;

    public UserService(UserRepository<User> userRepository, PassengerRepository passengerRepository, DriverRepository driverRepository) {
        this.userRepository = userRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
    }


    public User findByEmail(String username, String role) {
        User user;
        if (role.equals("ROLE_DRIVER"))
            user = driverRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        else
            user = passengerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }

    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return (UserDetails) user;
        };
    }
}