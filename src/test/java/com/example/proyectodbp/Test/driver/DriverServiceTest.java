package com.example.proyectodbp.Test.driver;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.driver.domain.Driver;
import com.example.proyectodbp.driver.domain.DriverService;
import com.example.proyectodbp.driver.dto.DriverDto;
import com.example.proyectodbp.driver.dto.NewDriverRequestDto;
import com.example.proyectodbp.driver.infraestructure.DriverRepository;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
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

class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private BusRepository busRepository;

    @Mock
    private UserRepository<User> userRepository;

    @Mock
    private AuthorizationUtils authorizationUtils;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        driverService = new DriverService(driverRepository, busRepository, userRepository, authorizationUtils, applicationEventPublisher);
    }

    @Test
    void testCreateDriver_UserNotAuthorized() {
        when(authorizationUtils.getCurrentUserEmail()).thenReturn(null);
        NewDriverRequestDto driverDto = new NewDriverRequestDto();
        assertThrows(UnauthorizedOperationException.class, () -> driverService.createDriver(driverDto));
    }

    @Test
    void testGetDriver_DriverNotFound() {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> driverService.getDriverInfo(1L));
    }

    @Test
    void testUpdateDriver_DriverNotFound() {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.empty());
        DriverDto driverDto = new DriverDto();
        assertThrows(ResourceNotFoundException.class, () -> driverService.updateDriver(1L, driverDto));
    }

    @Test
    void testUpdateDriverBus_BusNotFound() {
        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(new Driver()));
        when(busRepository.findByPlate(anyString())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> driverService.updateDriverBus(1L, "ABC123"));
    }
}