package com.example.proyectodbp.Test.passenger;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.passenger.domain.Passenger;
import com.example.proyectodbp.passenger.domain.PassengerService;
import com.example.proyectodbp.passenger.dto.NewPassengerRequestDto;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.passenger.infraestructure.PassengerRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import com.example.proyectodbp.utils.AuthorizationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private UserRepository<User> userRepository;

    @Mock
    private AuthorizationUtils authorizationUtils;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private PassengerService passengerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passengerService = new PassengerService(passengerRepository, stationRepository, userRepository, authorizationUtils, new ModelMapper(), applicationEventPublisher);
    }

    @Test
    void testCreatePassenger_UserNotAuthorized() {
        when(authorizationUtils.getCurrentUserEmail()).thenReturn(null);
        NewPassengerRequestDto passengerDto = new NewPassengerRequestDto();
        assertThrows(UnauthorizedOperationException.class, () -> passengerService.createPassenger(passengerDto));
    }

    @Test
    void testCreatePassenger_PassengerAlreadyExists() {
        NewPassengerRequestDto passengerDto = new NewPassengerRequestDto();
        passengerDto.setEmail("test@example.com");

        when(authorizationUtils.getCurrentUserEmail()).thenReturn("driver@example.com");
        when(userRepository.findByEmail("driver@example.com")).thenReturn(Optional.of(new User()));
        when(passengerRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new Passenger()));

        assertThrows(UniqueResourceAlreadyExist.class, () -> passengerService.createPassenger(passengerDto));
    }

    @Test
    void testGetPassenger_PassengerNotFound() {
        when(passengerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> passengerService.getPassengerInfo(1L));
    }

    @Test
    void testUpdatePassenger_PassengerNotFound() {
        when(passengerRepository.findById(anyLong())).thenReturn(Optional.empty());
        PassengerDto passengerDto = new PassengerDto();
        assertThrows(ResourceNotFoundException.class, () -> passengerService.updatePassenger(1L, passengerDto));
    }

    @Test
    void testUpdatePassengerStation_StationNotFound() {
        when(passengerRepository.findById(anyLong())).thenReturn(Optional.of(new Passenger()));
        when(stationRepository.findByName(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> passengerService.updatePassengerStation(1L, "Station Name"));
    }
}