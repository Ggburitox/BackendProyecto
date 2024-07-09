package com.example.proyectodbp.bus.application;

import com.example.proyectodbp.bus.domain.BusService;
import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.dto.NewBusRequestDto;
import com.example.proyectodbp.route.dto.RouteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bus")
public class BusController {
    private final BusService busService;

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

    @GetMapping()
    public ResponseEntity<List<BusDto>> getBuses() {
        return ResponseEntity.ok(busService.getBuses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBus(@PathVariable Long id, @RequestBody BusDto busDto) {
        busService.updateBus(id, busDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{plate}")
    public ResponseEntity<Void> deleteBusByPlate(@PathVariable String plate) {
        busService.deleteBusByPlate(plate);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{plate}/route")
    public ResponseEntity<Void> patchBusRoute(@PathVariable String plate, @RequestBody RouteDto routeDto) {
        busService.patchBusRoute(plate, routeDto);
        return ResponseEntity.ok().build();
    }
}