package com.example.proyectodbp.Test.route;

import com.example.proyectodbp.route.application.RouteController;
import com.example.proyectodbp.route.domain.RouteService;
import com.example.proyectodbp.route.dto.NewRouteRequestDto;
import com.example.proyectodbp.route.dto.RouteDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RouteControllerTest {

    @Mock
    private RouteService routeService;

    @InjectMocks
    private RouteController routeController;

    private MockMvc mockMvc;
    private RouteDto routeDto;
    private NewRouteRequestDto newRouteRequestDto;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(routeController).build();

        routeDto = new RouteDto();
        routeDto.setName("Route 1");

        newRouteRequestDto = new NewRouteRequestDto();
        newRouteRequestDto.setName("Route 1");
    }

    @Test
    void testCreateRoute() throws Exception {
        when(routeService.createRoute(any(NewRouteRequestDto.class))).thenReturn("/routes/1");

        mockMvc.perform(post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Route 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/routes/1"));

        verify(routeService, times(1)).createRoute(any(NewRouteRequestDto.class));
    }

    @Test
    void testGetRoute() throws Exception {
        when(routeService.getRouteInfo(any(Long.class))).thenReturn(routeDto);

        mockMvc.perform(get("/routes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Route 1"));

        verify(routeService, times(1)).getRouteInfo(1L);
    }

    @Test
    void testUpdateRoute() throws Exception {
        mockMvc.perform(put("/routes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Route 1\"}"))
                .andExpect(status().isOk());

        verify(routeService, times(1)).updateRoute(any(Long.class), any(RouteDto.class));
    }

    @Test
    void testDeleteRoute() throws Exception {
        mockMvc.perform(delete("/routes/1"))
                .andExpect(status().isNoContent());

        verify(routeService, times(1)).deleteRoute(1L);
    }

    @Test
    void testAddStation() throws Exception {
        mockMvc.perform(patch("/routes/1/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"Station 1\""))
                .andExpect(status().isOk());

        verify(routeService, times(1)).addStation(any(Long.class), any(String.class));
    }
}