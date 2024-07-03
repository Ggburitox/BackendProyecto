package com.example.proyectodbp.driver.application;

import com.example.proyectodbp.bus.dto.NewBusRequestDto;
import com.example.proyectodbp.driver.domain.DriverService;
import com.example.proyectodbp.driver.dto.DriverResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private final DriverService driverService;

    @Autowired
    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDto> getDriver(@PathVariable Long id) {
        return ResponseEntity.ok(driverService.getDriverInfo(id));
    }

    @GetMapping("/me")
    public ResponseEntity<DriverResponseDto> getDriver() {
        return ResponseEntity.ok(driverService.getDriverOwnInfo());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putDriver(@PathVariable Long id, @RequestBody DriverResponseDto driver) {
        driverService.updateDriverInfo(id, driver);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/bus")
    public ResponseEntity<Void> patchDriverBus(@PathVariable Long id,@RequestBody NewBusRequestDto busDto){
        driverService.updateDriverBus(id, busDto);
        return ResponseEntity.ok().build();
    }
}