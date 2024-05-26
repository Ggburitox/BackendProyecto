package com.example.proyectodbp.bus.domain;

import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizeOperationException;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.user.domain.Role;
import com.example.proyectodbp.user.domain.User; // Correct User import
import com.example.proyectodbp.user.infraestructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.proyectodbp.utils.AuthorizationUtils;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private UserRepository<User> userRepository;

    @Autowired
    AuthorizationUtils authorizationUtils;

    public String createBus(BusDto Busdto) {
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
        if (busRepository.findByPlate(Busdto.getPlate()).isPresent()) {
            throw new ResourceNotFoundException("This bus already exists");
        }

        Bus newBus = new Bus();
        newBus.setPlate(Busdto.getPlate());
        newBus.setRoute(Busdto.getRoute());
        newBus.setStation(Busdto.getStation());
        busRepository.save(newBus);
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

        BusDto busDto = new BusDto();
        busDto.setPlate(bus.getPlate());
        busDto.setRoute(bus.getRoute());
        busDto.setStation(bus.getStation());
        return busDto;
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

    public BusDto updateBusRoute(Long id, String routeName) {
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

        BusDto busDto = new BusDto();
        busDto.setPlate(bus.getPlate());
        busDto.setRoute(bus.getRoute());
        busDto.setStation(bus.getStation());
        return busDto;
    }
}