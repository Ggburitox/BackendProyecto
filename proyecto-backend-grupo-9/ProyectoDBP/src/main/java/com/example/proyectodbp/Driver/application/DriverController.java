package com.example.proyectodbp.Driver.application;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Driver.domain.Driver;
import com.example.proyectodbp.Driver.domain.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable Long id) {
        return new ResponseEntity<>(driverService.getDriver(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Void> createDriver(@RequestBody Driver driver) {
        driverService.createDriver(driver);
        return new ResponseEntity<>(HttpStatus.CREATED);
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