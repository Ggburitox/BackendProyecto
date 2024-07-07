package com.example.proyectodbp.bus.domain;

import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.dto.NewBusRequestDto;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.example.proyectodbp.auth.utils.AuthorizationUtils;
import java.util.List;
import java.util.stream.Collectors;

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

        if (busRepository.findByPlate(busRequest.getPlate()).isPresent())
            throw new UniqueResourceAlreadyExist("This bus already exists");

        Bus newBus = modelMapper.map(busRequest, Bus.class);
        busRepository.save(newBus);
        return "/driver/"+newBus.getId();
    }

    public BusDto getBus(Long id) {

        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        return modelMapper.map(bus, BusDto.class);
    }

    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    public void updateBus(Long id, BusDto busDto) {
        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        if (busDto.getRoute() != null) {
            Route route = routeRepository
                    .findByName(busDto.getRoute().getName())
                    .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

            bus.setRoute(route);
        }

        bus.setPlate(busDto.getPlate());
        busRepository.save(bus);
    }

    public void updateBusRoute(Long id, RouteDto routeDto) {
        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        Route route = routeRepository
                .findByName(routeDto.getName())
                .orElseThrow(() -> new ResourceNotFoundException("This route does not exist"));

        bus.setRoute(route);
        route.addBus(bus);
        busRepository.save(bus);
        routeRepository.save(route);
    }

    public List<BusDto> getBusesByStation(String stationName) {
        Station station = stationRepository
                .findByName(stationName)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        List<Bus> buses = busRepository.findAllByStation(station);

        return buses.stream()
                .map(bus -> modelMapper.map(bus, BusDto.class))
                .collect(Collectors.toList());
    }
}
