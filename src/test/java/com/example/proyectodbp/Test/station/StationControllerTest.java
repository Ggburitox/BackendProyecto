package com.example.proyectodbp.Test.station;

import com.example.proyectodbp.Test.station.Infrasctructure.StationTestContainer;
import com.example.proyectodbp.station.appliaction.StationController;
import com.example.proyectodbp.station.domain.StationService;
import com.example.proyectodbp.station.dto.NewStationRequestDto;
import com.example.proyectodbp.station.dto.StationDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Asegurarse de usar SpringExtension para cargar el contexto de Spring
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StationControllerTest {

    @Mock
    private StationService stationService;

    @InjectMocks
    private StationController stationController;

    private MockMvc mockMvc;
    private StationDto stationDto;
    private NewStationRequestDto newStationRequestDto;

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
        mockMvc = MockMvcBuilders.standaloneSetup(stationController).build();

        stationDto = new StationDto();
        stationDto.setName("Station 1");

        newStationRequestDto = new NewStationRequestDto();
        newStationRequestDto.setName("Station 1");
    }

    @Test
    void testCreateStation() throws Exception {
        when(stationService.createStation(any(NewStationRequestDto.class))).thenReturn("/station/1");

        mockMvc.perform(post("/station")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Station 1\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/station/1"));

        verify(stationService, times(1)).createStation(any(NewStationRequestDto.class));
    }

    @Test
    void testGetStation() throws Exception {
        when(stationService.getStationInfo(any(Long.class))).thenReturn(stationDto);

        mockMvc.perform(get("/station/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Station 1"));

        verify(stationService, times(1)).getStationInfo(1L);
    }

    @Test
    void testUpdateStation() throws Exception {
        mockMvc.perform(patch("/station/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Station 1\"}"))
                .andExpect(status().isOk());

        verify(stationService, times(1)).updateStation(any(Long.class), any(StationDto.class));
    }

    @Test
    void testDeleteStation() throws Exception {
        mockMvc.perform(delete("/station/1"))
                .andExpect(status().isNoContent());

        verify(stationService, times(1)).deleteStation(1L);
    }

    @Test
    void testAddRoute() throws Exception {
        mockMvc.perform(patch("/station/1/route")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"Route 1\""))
                .andExpect(status().isOk());

        verify(stationService, times(1)).addRoute(any(Long.class), any(String.class));
    }
}