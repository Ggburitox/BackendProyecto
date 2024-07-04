package com.example.proyectodbp.bus.domain;

import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.dto.NewBusRequestDto;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UnauthorizedOperationException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.example.proyectodbp.auth.utils.AuthorizationUtils;

@Service
public class BusService {
    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper = new ModelMapper();
    private final ApplicationEventPublisher applicationEventPublisher;

    public BusService(BusRepository busRepository, RouteRepository routeRepository, AuthorizationUtils authorizationUtils, ApplicationEventPublisher applicationEventPublisher, StationRepository stationRepository) {
        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.authorizationUtils = authorizationUtils;
        this.applicationEventPublisher = applicationEventPublisher;
        this.stationRepository = stationRepository;
    }
  
    public String createBus(NewBusRequestDto busRequest) {
        if (!authorizationUtils.isAdmin())
            throw new UnauthorizedOperationException("User has no permission to create a bus");

        if (busRepository.findByPlate(busRequest.getPlate()).isPresent())
            throw new UniqueResourceAlreadyExist("This bus already exists");

        Bus newBus = modelMapper.map(busRequest, Bus.class);
        busRepository.save(newBus);
        return "/driver/"+newBus.getId();
    }

    public BusDto getBus(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        return modelMapper.map(bus, BusDto.class);
    }

    public void deleteBus(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        busRepository.deleteById(id);
    }

    public void updateBus(Long id, BusDto busDto) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        Route route = routeRepository
                .findByName(busDto.getRoute().getName())
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        Station station = stationRepository
                .findByName(busDto.getStation().getName())
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        bus.setPlate(busDto.getPlate());
        bus.setRoute(route);
        bus.setStation(station);
        busRepository.save(bus);
    }

    public void updateBusRoute(Long id, String routeName) {
        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizedOperationException("User has no permission to modify this resource");

        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        Route route = routeRepository
                .findByName(routeName)
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        bus.setRoute(route);
        route.addBus(bus);
        busRepository.save(bus);
        routeRepository.save(route);
//        String message = "Usted ha actualizado la ruta de su bus!";
//        applicationEventPublisher.publishEvent(new HelloEmailEvent(bus.getDriver().getEmail(), message));
    }
}