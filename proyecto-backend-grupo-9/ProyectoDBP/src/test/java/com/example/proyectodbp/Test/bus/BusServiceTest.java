package com.example.proyectodbp.Test.bus;

import com.example.proyectodbp.bus.domain.BusService;
import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.dto.NewBusRequestDto;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private UserRepository<User> userRepository;

    @Mock
    private AuthorizationUtils authorizationUtils;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private BusService busService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        busService = new BusService(busRepository, routeRepository, userRepository, authorizationUtils, applicationEventPublisher);
    }

    @Test
    void testCreateBus_UserNotAuthorized() {
        when(authorizationUtils.getCurrentUserEmail()).thenReturn(null);
        NewBusRequestDto busDto = new NewBusRequestDto();
        assertThrows(UnauthorizedOperationException.class, () -> busService.createBus(busDto));
    }

    @Test
    void testGetBus_BusNotFound() {
        when(busRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> busService.getBus(1L));
    }

    @Test
    void testUpdateBus_BusNotFound() {
        when(busRepository.findById(anyLong())).thenReturn(Optional.empty());
        BusDto busDto = new BusDto();
        assertThrows(ResourceNotFoundException.class, () -> busService.updateBus(1L, busDto));
    }
}