package com.example.proyectodbp.Test.passenger;

import com.example.proyectodbp.passenger.application.PassengerController;
import com.example.proyectodbp.passenger.domain.PassengerService;
import com.example.proyectodbp.passenger.dto.NewPassengerRequestDto;
import com.example.proyectodbp.passenger.dto.PassengerDto;
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

@WebMvcTest(PassengerController.class)
class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassengerService passengerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePassenger() throws Exception {
        NewPassengerRequestDto passengerDto = new NewPassengerRequestDto();
        passengerDto.setEmail("passenger@example.com");

        when(passengerService.createPassenger(any(NewPassengerRequestDto.class))).thenReturn("/passenger/1");

        mockMvc.perform(post("/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"passenger@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"password\": \"password\", \"dni\": \"12345678\", \"station\": {\"id\": 1}}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/passenger/1"));
    }

    @Test
    void testGetPassenger() throws Exception {
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setEmail("passenger@example.com");

        when(passengerService.getPassengerInfo(anyLong())).thenReturn(passengerDto);

        mockMvc.perform(get("/passenger/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("passenger@example.com"));
    }

    @Test
    void testUpdatePassenger() throws Exception {
        mockMvc.perform(put("/passenger/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"passenger@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"dni\": \"12345678\", \"station\": {\"id\": 1}}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeletePassenger() throws Exception {
        mockMvc.perform(delete("/passenger/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPatchPassengerStation() throws Exception {
        mockMvc.perform(patch("/passenger/1/station")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stationName\": \"Central Station\"}"))
                .andExpect(status().isOk());
    }
}