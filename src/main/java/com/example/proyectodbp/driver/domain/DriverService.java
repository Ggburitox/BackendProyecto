package com.example.proyectodbp.driver.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.driver.dto.DriverDto;
import com.example.proyectodbp.driver.infraestructure.DriverRepository;
import com.example.proyectodbp.events.HelloEmailEvent;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.auth.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final BusRepository busRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DriverService(DriverRepository driverRepository, BusRepository busRepository, AuthorizationUtils authorizationUtils, ApplicationEventPublisher applicationEventPublisher) {
        this.driverRepository = driverRepository;
        this.busRepository = busRepository;
        this.authorizationUtils = authorizationUtils;
        this.applicationEventPublisher = applicationEventPublisher;
        this.modelMapper = new ModelMapper();
    }

    public DriverDto getDriverInfo(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Driver driver = driverRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This driver does not exist"));

        return modelMapper.map(driver, DriverDto.class);
    }

    public DriverDto getDriverOwnInfo() {
        String email = authorizationUtils.getCurrentUserEmail();
        if (email==null) {
            throw new UnauthorizedOperationException("Anonymus user not allowed to access this resource");
        }
        Driver driver = driverRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Driver not found"));
        return getDriverInfo(driver.getId());
    }

    public void deleteDriver(String email) {
        Driver driver = driverRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Driver not found"));

        driverRepository.delete(driver);
    }

    public void updateDriverInfo(Long id, DriverDto driverInfo) {
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

    public void updateDriverBus(String email, BusDto busDto) {
        Driver driver = driverRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Driver not found"));

        if(driver.getBus() != null){
            Bus oldBus = driver.getBus();
            driver.setBus(null);
            oldBus.setDriver(null);
            driverRepository.save(driver);
            busRepository.save(oldBus);
        }

        Bus newBus = busRepository
                .findByPlate(busDto.getPlate())
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found"));

        driver.setBus(newBus);
        newBus.setDriver(driver);
        driverRepository.save(driver);
        busRepository.save(newBus);
    }
}