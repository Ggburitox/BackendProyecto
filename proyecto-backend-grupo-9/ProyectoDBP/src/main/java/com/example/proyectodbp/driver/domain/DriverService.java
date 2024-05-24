package com.example.proyectodbp.driver.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.driver.infraestructure.DriverRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public Driver getDriver(Long id) {
        return driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("driver not found"));
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }


    public void createDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public void deleteDriver(Long id) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("driver not found"));
        driverRepository.delete(driver);
    }

    public Driver updateDriver(Long id, Driver driver) {
        Driver driverToUpdate = driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("driver not found"));
        driverToUpdate.setFirstName(driver.getFirstName());
        driverToUpdate.setLastName(driver.getLastName());
        driverToUpdate.setEmail(driver.getEmail());
        driverToUpdate.setPassword(driver.getPassword());
        driverToUpdate.setPhoneNumber(driver.getPhoneNumber());
        driverToUpdate.setDni(driver.getDni());
        driverRepository.save(driverToUpdate);
        return driverToUpdate;
    }

    public void updateDriverBus(Long id, Bus bus) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("driver not found"));
        driver.setBus(bus);
        driverRepository.save(driver);
    }
}