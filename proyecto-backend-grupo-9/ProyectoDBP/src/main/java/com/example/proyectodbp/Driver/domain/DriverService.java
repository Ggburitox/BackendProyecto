package com.example.proyectodbp.Driver.domain;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Driver.infraestructure.DriverRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public Driver getDriver(Long id) {
        return driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }

    public void createDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public void deleteDriver(Long id) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        driverRepository.delete(driver);
    }

    public Driver updateDriver(Long id, Driver driver) {
        Driver driverToUpdate = driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
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
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        driver.setBus(bus);
        driverRepository.save(driver);
    }
}