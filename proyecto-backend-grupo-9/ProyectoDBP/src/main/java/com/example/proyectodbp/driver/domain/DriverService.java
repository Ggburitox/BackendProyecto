package com.example.proyectodbp.driver.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.driver.dto.DriverDto;
import com.example.proyectodbp.driver.infraestructure.DriverRepository;

import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BusRepository busRepository;

    public DriverDto getDriverInfo(Long id) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This driver does not exist"));

        DriverDto driverDto = new DriverDto();
        driverDto.setFirstName(driver.getFirstName());
        driverDto.setLastName(driver.getLastName());
        driverDto.setEmail(driver.getEmail());
        driverDto.setDni(driver.getDni());
        return driverDto;
    }

    public String createDriver(DriverDto driver) {
        if (driverRepository.findByEmail(driver.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This driver already exists");
        }
        Driver newDriver = new Driver();
        newDriver.setFirstName(driver.getFirstName());
        newDriver.setLastName(driver.getLastName());
        newDriver.setEmail(driver.getEmail());
        newDriver.setDni(driver.getDni());
        driverRepository.save(newDriver);
        return "/driver/"+newDriver.getId();
    }

    public void deleteDriver(Long id) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new UniqueResourceAlreadyExist("driver not found"));
        driverRepository.delete(driver);
    }

    public Driver updateDriver(Long id, Driver driver) {
        Driver driverToUpdate = driverRepository
                .findById(id)
                .orElseThrow(() -> new UniqueResourceAlreadyExist("This driver does not exist"));
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
                .orElseThrow(() -> new UniqueResourceAlreadyExist("This driver does not exist"));
        driver.setBus(bus);
        driverRepository.save(driver);
    }
}