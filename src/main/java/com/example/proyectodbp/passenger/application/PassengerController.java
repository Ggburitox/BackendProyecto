package com.example.proyectodbp.passenger.application;

import com.example.proyectodbp.passenger.domain.PassengerService;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.station.dto.StationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passenger")
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getPassenger(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.getPassengerInfo(id));
    }

    @GetMapping("/me")
    public ResponseEntity<PassengerDto> getPassenger() {
        return ResponseEntity.ok(passengerService.getPassengerOwnInfo());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putPassenger(@PathVariable Long id, @RequestBody PassengerDto passenger) {
        passengerService.updatePassenger(id, passenger);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deletePassenger() {
        passengerService.deletePassenger();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/station")
    public ResponseEntity<Void> patchPassengerStation(@RequestBody StationDto stationDto) {
        passengerService.updatePassengerStation(stationDto);
        return ResponseEntity.ok().build();
    }
}