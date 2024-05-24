package com.example.proyectodbp.bus.application;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.bus.domain.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus")
public class BusController {
    @Autowired
    private BusService busService;

    @PostMapping()
    public ResponseEntity<Void> createBus(@RequestBody Bus bus) {
        busService.createBus(bus);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBus(@PathVariable Long id) {
        return new ResponseEntity<>(busService.getBus(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBus(@PathVariable Long id, @RequestBody Bus bus) {
        busService.updateBus(id, bus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
