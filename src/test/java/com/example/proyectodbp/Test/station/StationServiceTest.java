package com.example.proyectodbp.Test.station;

import com.example.proyectodbp.Test.station.Infrasctructure.StationTestContainer;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.domain.StationService;
import com.example.proyectodbp.station.dto.NewStationRequestDto;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import com.example.proyectodbp.utils.AuthorizationUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private UserRepository<User> userRepository;

    @Mock
    private AuthorizationUtils authorizationUtils;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StationService stationService;

    private User driver;
    private Station station;
    private NewStationRequestDto newStationRequestDto;
    private StationDto stationDto;
    private Route route;

    @BeforeAll
    static void startContainer() {
        StationTestContainer.getInstance().start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", StationTestContainer.getInstance()::getJdbcUrl);
        registry.add("spring.datasource.username", StationTestContainer.getInstance()::getUsername);
        registry.add("spring.datasource.password", StationTestContainer.getInstance()::getPassword);
    }

    @BeforeEach
    void setUp() {
        driver = new User();
        driver.setRole(Role.DRIVER);
        driver.setEmail("driver@example.com");

        station = new Station();
        station.setId(1L);
        station.setName("Station 1");

        newStationRequestDto = new NewStationRequestDto();
        newStationRequestDto.setName("Station 1");

        stationDto = new StationDto();
        stationDto.setName("Station 1");

        route = new Route();
        route.setName("Route 1");
    }

    @Test
    void testCreateStation() {
        when(authorizationUtils.getCurrentUserEmail()).thenReturn("driver@example.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(driver));
        when(stationRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(modelMapper.map(any(NewStationRequestDto.class), eq(Station.class))).thenReturn(station);

        String result = stationService.createStation(newStationRequestDto);

        assertEquals("/station/1", result);
        verify(stationRepository, times(1)).save(any(Station.class));
    }

    @Test
    void testGetStationInfo() {
        when(stationRepository.findById(any(Long.class))).thenReturn(Optional.of(station));
        when(modelMapper.map(any(Station.class), eq(StationDto.class))).thenReturn(stationDto);
        when(authorizationUtils.isAdminOrResourceOwner(any(Long.class))).thenReturn(true);

        StationDto result = stationService.getStationInfo(1L);

        assertEquals("Station 1", result.getName());
        verify(stationRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateStation() {
        when(stationRepository.findById(any(Long.class))).thenReturn(Optional.of(station));
        when(authorizationUtils.isAdminOrResourceOwner(any(Long.class))).thenReturn(true);

        stationService.updateStation(1L, stationDto);

        verify(stationRepository, times(1)).save(station);
    }

    @Test
    void testDeleteStation() {
        stationService.deleteStation(1L);
        verify(stationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAddRoute() {
        when(stationRepository.findById(any(Long.class))).thenReturn(Optional.of(station));
        when(routeRepository.findByName(any(String.class))).thenReturn(Optional.of(route));
        when(authorizationUtils.isAdminOrResourceOwner(any(Long.class))).thenReturn(true);

        stationService.addRoute(1L, "Route 1");

        verify(stationRepository, times(1)).save(station);
        verify(routeRepository, times(1)).save(route);
    }
}