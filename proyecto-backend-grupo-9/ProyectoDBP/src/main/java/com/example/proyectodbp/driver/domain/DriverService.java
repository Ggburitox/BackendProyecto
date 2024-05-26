package com.example.proyectodbp.driver.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.driver.dto.DriverDto;
import com.example.proyectodbp.driver.dto.NewDriverRequestDto;
import com.example.proyectodbp.driver.infraestructure.DriverRepository;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final BusRepository busRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public DriverService(DriverRepository driverRepository, BusRepository busRepository) {
        this.driverRepository = driverRepository;
        this.busRepository = busRepository;
        this.modelMapper = new ModelMapper();
    }

    public DriverDto getDriverInfo(Long id) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This driver does not exist"));

        return modelMapper.map(driver, DriverDto.class);
    }

    public String createDriver(NewDriverRequestDto requestDto) {
        if (driverRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This requestDto already exists");
        }
        Driver newDriver = modelMapper.map(requestDto, Driver.class);
        driverRepository.save(newDriver);
        return "/requestDto/" + newDriver.getId();
    }

    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }

    public void updateDriver(Long id, DriverDto driver) {
        Driver driverToUpdate = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This driver does not exist"));
        driverToUpdate.setFirstName(driver.getFirstName());
        driverToUpdate.setLastName(driver.getLastName());
        driverToUpdate.setEmail(driver.getEmail());
        driverToUpdate.setDni(driver.getDni());
        driverRepository.save(driverToUpdate);
    }

    public void updateDriverBus(Long id, String busPlate) {
        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This driver does not exist"));
        Bus bus = busRepository
                .findByPlate(busPlate)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        bus.setDriver(driver);
        driver.setBus(bus);
        busRepository.save(bus);
        driverRepository.save(driver);
    }
}