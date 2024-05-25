package com.example.proyectodbp.driver.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.driver.infraestructure.DriverRepository;

import com.example.proyectodbp.exceptions.EntityAlreadyExists;
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
                .orElseThrow(() -> new EntityAlreadyExists("This driver does not exist"));
    }

    public List<Driver> getAllDrivers() {
            List<Driver> drivers = driverRepository.findAll();
            if (drivers.isEmpty()) {
                throw new EntityAlreadyExists("There are no drivers in the database");
            }
            return drivers;
    }


    public void createDriver(Driver driver) {
        driverRepository.save(driver);
    }

    public void deleteDriver(Long id) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("driver not found"));
        driverRepository.delete(driver);
    }

    public Driver updateDriver(Long id, Driver driver) {
        Driver driverToUpdate = driverRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This driver does not exist"));
        driverToUpdate.setFirstName(driver.getFirstName());
        driverToUpdate.setLastName(driver.getLastName());
        driverToUpdate.setEmail(driver.getEmail());
        driverToUpdate.setPassword(driver.getPassword());
        driverToUpdate.setDni(driver.getDni());
        driverRepository.save(driverToUpdate);
        return driverToUpdate;
    }

    public void updateDriverBus(Long id, Bus bus) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This driver does not exist"));
        driver.setBus(bus);
        driverRepository.save(driver);
    }
}