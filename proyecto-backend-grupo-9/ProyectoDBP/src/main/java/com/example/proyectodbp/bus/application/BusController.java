package com.example.proyectodbp.bus.application;

import com.example.proyectodbp.bus.domain.BusService;
import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.dto.NewBusRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/bus")
public class BusController {
    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping()
    public ResponseEntity<Void> createBus(@RequestBody NewBusRequestDto busDto) {
        return ResponseEntity.created(URI.create(busService.createBus(busDto))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusDto> getBus(@PathVariable Long id) {
        return ResponseEntity.ok(busService.getBus(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBus(@PathVariable Long id, @RequestBody BusDto busDto) {
        busService.updateBus(id, busDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/route")
    public ResponseEntity<Void> patchBusRoute(@PathVariable Long id, @RequestBody String routeName){
        busService.updateBusRoute(id, routeName);
        return ResponseEntity.ok().build();
    }
}