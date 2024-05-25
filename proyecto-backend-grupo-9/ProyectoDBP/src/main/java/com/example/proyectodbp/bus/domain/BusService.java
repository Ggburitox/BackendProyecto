package com.example.proyectodbp.bus.domain;

import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.infraestructure.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private RouteRepository routeRepository;

    public String createBus(BusDto Busdto) {
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
        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

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
        Bus busToUpdate = busRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This bus does not exist"));

        busToUpdate.setPlate(busDto.getPlate());
        busToUpdate.setRoute(busDto.getRoute());
        busToUpdate.setStation(busDto.getStation());
        busRepository.save(busToUpdate);
    }

    public BusDto updateBusRoute(Long id, String routeName) {
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
        busDto.setRoute(bus.getRoute());
        return busDto;
    }
}