package com.example.proyectodbp.Test.route;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.domain.RouteService;
import com.example.proyectodbp.route.dto.NewRouteRequestDto;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import com.example.proyectodbp.utils.AuthorizationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {

    @Mock
    private RouteRepository routeRepository;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private UserRepository<User> userRepository;

    @Mock
    private AuthorizationUtils authorizationUtils;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RouteService routeService;

    private User driver;
    private Route route;
    private NewRouteRequestDto newRouteRequestDto;
    private RouteDto routeDto;
    private Station station;

    @BeforeEach
    void setUp() {
        driver = new User();
        driver.setRole(Role.DRIVER);
        driver.setEmail("driver@example.com");

        route = new Route();
        route.setId(1L);
        route.setName("Route 1");

        newRouteRequestDto = new NewRouteRequestDto();
        newRouteRequestDto.setName("Route 1");

        routeDto = new RouteDto();
        routeDto.setName("Route 1");

        station = new Station();
        station.setName("Station 1");
    }

    @Test
    void testCreateRoute() {
        when(authorizationUtils.getCurrentUserEmail()).thenReturn("driver@example.com");
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(driver));
        when(routeRepository.findByName(any(String.class))).thenReturn(Optional.empty());
        when(modelMapper.map(any(NewRouteRequestDto.class), eq(Route.class))).thenReturn(route);

        String result = routeService.createRoute(newRouteRequestDto);

        assertEquals("/routes/1", result);
        verify(routeRepository, times(1)).save(any(Route.class));
    }

    @Test
    void testGetRouteInfo() {
        when(routeRepository.findById(any(Long.class))).thenReturn(Optional.of(route));
        when(modelMapper.map(any(Route.class), eq(RouteDto.class))).thenReturn(routeDto);
        when(authorizationUtils.isAdminOrResourceOwner(any(Long.class))).thenReturn(true);

        RouteDto result = routeService.getRouteInfo(1L);

        assertEquals("Route 1", result.getName());
        verify(routeRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateRoute() {
        when(routeRepository.findById(any(Long.class))).thenReturn(Optional.of(route));
        when(authorizationUtils.isAdminOrResourceOwner(any(Long.class))).thenReturn(true);

        routeService.updateRoute(1L, routeDto);

        verify(routeRepository, times(1)).save(route);
    }

    @Test
    void testDeleteRoute() {
        routeService.deleteRoute(1L);
        verify(routeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAddStation() {
        when(routeRepository.findById(any(Long.class))).thenReturn(Optional.of(route));
        when(stationRepository.findByName(any(String.class))).thenReturn(Optional.of(station));
        when(authorizationUtils.isAdminOrResourceOwner(any(Long.class))).thenReturn(true);

        routeService.addStation(1L, "Station 1");

        verify(routeRepository, times(1)).save(route);
        verify(stationRepository, times(1)).save(station);
    }
}