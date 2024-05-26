package com.example.proyectodbp.bus.domain;

import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.dto.NewBusRequestDto;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizeOperationException;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User;
import com.example.proyectodbp.user.infraestructure.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.proyectodbp.utils.AuthorizationUtils;

@Service
public class BusService {
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final UserRepository<User> userRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;

    @Autowired
    public BusService(BusRepository busRepository, RouteRepository routeRepository, UserRepository<User> userRepository, AuthorizationUtils authorizationUtils) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.userRepository = userRepository;
        this.authorizationUtils = authorizationUtils;
        this.modelMapper = new ModelMapper();
    }
  
    public String createBus(NewBusRequestDto busDto) {
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
        if (busRepository.findByPlate(busDto.getPlate()).isPresent()) {
            throw new ResourceNotFoundException("This bus already exists");
        }
        Bus newBus = modelMapper.map(busDto, Bus.class);
        return "/driver/"+newBus.getId();
    }

    public BusDto getBus(Long id) {
        // Verifica si el usuario actual es un administrador o el propietario del recurso
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }
        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Este bus no existe"));

        return modelMapper.map(bus, BusDto.class);
    }

    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    public void updateBus(Long id, BusDto busDto) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }
        Bus busToUpdate = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        busToUpdate.setPlate(busDto.getPlate());
        busToUpdate.setRoute(busDto.getRoute());
        busToUpdate.setStation(busDto.getStation());
        busRepository.save(busToUpdate);
    }

    public void updateBusRoute(Long id, String routeName) {
        // Check if the current user is an admin or the owner of the resource
        if(!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("No estas autorizado para acceder a este recurso");
        }
        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        Route route = routeRepository
                .findByName(routeName)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        bus.setRoute(route);
        route.getBuses().add(bus);
        busRepository.save(bus);
        routeRepository.save(route);
    }
}