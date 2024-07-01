package com.example.proyectodbp.driver.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.driver.dto.DriverResponseDto;
import com.example.proyectodbp.driver.infraestructure.DriverRepository;
import com.example.proyectodbp.events.HelloEmailEvent;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.auth.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final BusRepository busRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public DriverService(DriverRepository driverRepository, BusRepository busRepository, AuthorizationUtils authorizationUtils, ApplicationEventPublisher applicationEventPublisher) {
        this.driverRepository = driverRepository;
        this.busRepository = busRepository;
        this.authorizationUtils = authorizationUtils;
        this.applicationEventPublisher = applicationEventPublisher;
        this.modelMapper = new ModelMapper();
    }

    public DriverResponseDto getDriverInfo(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This driver does not exist"));

        return modelMapper.map(driver, DriverResponseDto.class);
    }

    public void deleteDriver(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        driverRepository.deleteById(id);
    }

    public void updateDriverInfo(Long id, DriverResponseDto driverInfo) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        driver.setFirstName(driverInfo.getFirstName());
        driver.setLastName(driverInfo.getLastName());
        driver.setEmail(driverInfo.getEmail());
        driverRepository.save(driver);

        String message = "Sus datos han sido actualizados";
        applicationEventPublisher.publishEvent(new HelloEmailEvent(driverInfo.getEmail(), message));
    }

    public void updateDriverBus(Long id, String busPlate) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

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
        String message = "Su bus ha sido actualizado!";
        applicationEventPublisher.publishEvent(new HelloEmailEvent(driver.getEmail(), message));
    }
}