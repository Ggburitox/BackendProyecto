package com.example.proyectodbp.Test.bus;

import com.example.proyectodbp.bus.domain.BusService;
import com.example.proyectodbp.bus.application.BusController;
import com.example.proyectodbp.bus.domain.BusService;
import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.dto.NewBusRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BusController.class)
class BusControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusService busService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBus() throws Exception {
        NewBusRequestDto busDto = new NewBusRequestDto();
        busDto.setPlate("ABC123");

        when(busService.createBus(any(NewBusRequestDto.class))).thenReturn("/bus/1");

        mockMvc.perform(post("/bus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"plate\": \"ABC123\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/bus/1"));
    }

    @Test
    void testGetBus() throws Exception {
        BusDto busDto = new BusDto();
        busDto.setPlate("ABC123");

        when(busService.getBus(anyLong())).thenReturn(busDto);

        mockMvc.perform(get("/bus/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.plate").value("ABC123"));
    }

    @Test
    void testUpdateBus() throws Exception {
        mockMvc.perform(put("/bus/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"plate\": \"DEF456\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteBus() throws Exception {
        mockMvc.perform(delete("/bus/1"))
                .andExpect(status().isNoContent());
    }
}