package com.example.proyectodbp.Bus.application;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Bus.domain.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus")
public class BusController {
    @Autowired
    private BusService busService;

    @PostMapping()
    public void createBus(@RequestBody Bus bus, @RequestParam Long busId) {
        busService.createBus(bus, busId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBus(@PathVariable Long id) {
        return ResponseEntity.ok(busService.getBus(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable Long id, @RequestBody Bus bus) {
        return ResponseEntity.ok(busService.updateBus(id, bus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.noContent().build();
    }

}
