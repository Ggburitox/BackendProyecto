package com.example.proyectodbp.auth.domain;

import com.example.proyectodbp.config.JwtService;
import com.example.proyectodbp.auth.dto.JwtAuthResponse;
import com.example.proyectodbp.auth.dto.LoginRequest;
import com.example.proyectodbp.auth.dto.RegisterRequest;
import com.example.proyectodbp.driver.infraestructure.DriverRepository;
import com.example.proyectodbp.exceptions.UserAlreadyExistException;
import com.example.proyectodbp.driver.domain.Driver;
import com.example.proyectodbp.passenger.domain.Passenger;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository<User> userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final DriverRepository driverRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public AuthService(UserRepository<User> userRepository, JwtService jwtService, PasswordEncoder  passwordEncoder, DriverRepository driverRepository, PassengerRepository passengerRepository){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
        this.driverRepository = driverRepository;
        this.passengerRepository = passengerRepository;
    }
    public JwtAuthResponse login(LoginRequest request){
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if(!passwordEncoder.matches(request.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }

    public JwtAuthResponse register(RegisterRequest request){
        Optional<User> user =userRepository.findByEmail(request.getEmail());
        if(user.isPresent()) throw new UserAlreadyExistException("Email is already registered");

        if(request.getIsDriver()){
            Driver driver = new Driver();
            driver.setRole(Role.DRIVER);
            driver.setFirstName(request.getName());
            driver.setLastName(request.getLastName());
            driver.setEmail(request.getEmail());
            driver.setPassword(passwordEncoder.encode(request.getPassword()));

            driverRepository.save(driver);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(driver));
            return response;

        }
        else{
            Passenger passenger = new Passenger();
            passenger.setRole(Role.PASSENGER);
            passenger.setFirstName(request.getName());
            passenger.setLastName(request.getLastName());
            passenger.setEmail(request.getEmail());
            passenger.setPassword(passwordEncoder.encode(request.getPassword()));

            passengerRepository.save(passenger);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(passenger));
            return response;

        }
    }
}