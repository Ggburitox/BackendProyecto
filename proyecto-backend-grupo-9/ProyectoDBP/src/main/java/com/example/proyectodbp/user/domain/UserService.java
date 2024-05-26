package com.example.proyectodbp.user.domain;

import com.example.proyectodbp.driver.infraestructure.DriverRepository;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository<User> userRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private DriverRepository driverRepository;


    public User findByEmail(String username, String role) {
        User user;
        if (role.equals("ROLE_DRIVE"))
            user = driverRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        else
            user = passengerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user;
    }

    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return (UserDetails) user;
        };
    }
}
