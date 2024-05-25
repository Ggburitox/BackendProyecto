package com.example.proyectodbp.driver.application;

import com.example.proyectodbp.driver.domain.DriverService;
import com.example.proyectodbp.driver.dto.DriverDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverInfo(id));
    }

    @PostMapping()
    public ResponseEntity<Void> createDriver(@RequestBody DriverDto driver) {
        return ResponseEntity.created(URI.create(driverService.createDriver(driver))).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putDriver(@PathVariable Long id, @RequestBody DriverDto driver) {
        driverService.updateDriver(id, driver);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/bus")
    public ResponseEntity<DriverDto> patchDriverBus(@PathVariable Long id,@RequestBody String busPlate){
        return ResponseEntity.ok(driverService.updateDriverBus(id, busPlate));
    }
}