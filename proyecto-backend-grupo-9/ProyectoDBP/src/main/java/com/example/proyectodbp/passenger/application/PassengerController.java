package com.example.proyectodbp.passenger.application;

import com.example.proyectodbp.passenger.domain.Passenger;
import com.example.proyectodbp.passenger.domain.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passenger")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @PostMapping
    public ResponseEntity<Void> createPassenger(@RequestBody Passenger passenger) {
        passengerService.createPassenger(passenger);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassenger(Long id) {
        return new ResponseEntity<>(passengerService.getPassenger(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putPassenger(@PathVariable Long id, @RequestBody Passenger passenger) {
        passengerService.updatePassenger(id, passenger);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
