package com.example.proyectodbp.Test.driver;

import com.example.proyectodbp.driver.application.DriverController;
import com.example.proyectodbp.driver.domain.DriverService;
import com.example.proyectodbp.driver.dto.DriverDto;
import com.example.proyectodbp.driver.dto.NewDriverRequestDto;
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

@WebMvcTest(DriverController.class)
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDriver() throws Exception {
        NewDriverRequestDto driverDto = new NewDriverRequestDto();
        driverDto.setEmail("driver@example.com");

        when(driverService.createDriver(any(NewDriverRequestDto.class))).thenReturn("/driver/1");

        mockMvc.perform(post("/driver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"driver@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"password\": \"password\", \"dni\": \"12345678\", \"bus\": {\"id\": 1}}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/driver/1"));
    }

    @Test
    void testGetDriver() throws Exception {
        DriverDto driverDto = new DriverDto();
        driverDto.setEmail("driver@example.com");

        when(driverService.getDriverInfo(anyLong())).thenReturn(driverDto);

        mockMvc.perform(get("/driver/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("driver@example.com"));
    }

    @Test
    void testUpdateDriver() throws Exception {
        mockMvc.perform(put("/driver/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"driver@example.com\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"dni\": \"12345678\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteDriver() throws Exception {
        mockMvc.perform(delete("/driver/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPatchDriverBus() throws Exception {
        mockMvc.perform(patch("/driver/1/bus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"busPlate\": \"ABC123\"}"))
                .andExpect(status().isOk());
    }
}