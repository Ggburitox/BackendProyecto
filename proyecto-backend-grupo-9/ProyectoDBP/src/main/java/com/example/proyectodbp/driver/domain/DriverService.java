package com.example.proyectodbp.driver.domain;

import com.example.proyectodbp.bus.domain.Bus;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.driver.dto.DriverDto;
import com.example.proyectodbp.driver.dto.NewDriverRequestDto;
import com.example.proyectodbp.driver.infraestructure.DriverRepository;
import com.example.proyectodbp.exceptions.UnauthorizeOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import com.example.proyectodbp.utils.AuthorizationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private AuthorizationUtils authorizationUtils;

    @Autowired
    private UserRepository<User> userRepository;


    public DriverDto getDriverInfo(Long id) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }

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

    public String createDriver(NewDriverRequestDto requestDto) {
        // Aquí obtienes el identificador del usuario actual (correo electrónico) utilizando Spring Security
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null) {
            throw new UnauthorizeOperationException("Anonymous User not allowed to access");
        }

        // Verifica que el usuario actual sea un DRIVER
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user.getRole() != Role.DRIVER) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }

        if (driverRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UniqueResourceAlreadyExist("This driver already exists");
        }

        Driver newDriver = new Driver();
        newDriver.setRole(requestDto.getRole());
        newDriver.setFirstName(requestDto.getFirstName());
        newDriver.setLastName(requestDto.getLastName());
        newDriver.setEmail(requestDto.getEmail());
        newDriver.setPassword(requestDto.getPassword());
        newDriver.setDni(requestDto.getDni());
        newDriver.setBus(requestDto.getBus());
        driverRepository.save(newDriver);
        return "/requestDto/" + newDriver.getId();
    }

    public void deleteDriver(Long id) {
        driverRepository.deleteById(id);
    }

    public void updateDriver(Long id, DriverDto driver) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }

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
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }

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