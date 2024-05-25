package com.example.proyectodbp.driver.application;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.driver.domain.Driver;
import com.example.proyectodbp.driver.domain.DriverService;
import com.example.proyectodbp.driver.dto.DriverDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable Long id) {
        return new ResponseEntity<>(driverService.getDriverInfo(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Void> createDriver(@RequestBody DriverDto driver) {
        return ResponseEntity.created(URI.create(driverService.createDriver(driver))).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putDriver(@PathVariable Long id, @RequestBody Driver driver) {
        driverService.updateDriver(id, driver);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}/bus")
    public ResponseEntity<Void> patchDriver(@PathVariable Long id, Bus bus){
        driverService.updateDriverBus(id, bus);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}