package com.example.proyectodbp.bus.application;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.bus.domain.BusService;
import com.example.proyectodbp.bus.dto.BusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/bus")
public class BusController {
    @Autowired
    private BusService busService;

    @PostMapping()
    public ResponseEntity<Void> createBus(@RequestBody BusDto busdto) {
        return ResponseEntity.created(URI.create(busService.createBus(busdto))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusDto> getBus(@PathVariable Long id) {
        return new ResponseEntity<>(busService.getBus(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBus(@PathVariable Long id, @RequestBody BusDto busDto) {
        busService.updateBus(id, busDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
