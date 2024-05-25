package com.example.proyectodbp.bus.domain;

import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.exceptions.EntityAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public String createBus(BusDto Busdto) {
        if (busRepository.findByPlate(Busdto.getPlate()).isPresent()) {
            throw new EntityAlreadyExists("This bus already exists");
        }

        Bus newBus = new Bus();
        newBus.setPlate(Busdto.getPlate());
        newBus.setRoute(Busdto.getRoute());
        busRepository.save(newBus);
        return "/driver/"+newBus.getId();
    }

    public BusDto getBus(Long id) {
        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This bus does not exist"));

        BusDto busDto = new BusDto();
        busDto.setPlate(bus.getPlate());
        busDto.setRoute(bus.getRoute());
        return busDto;
    }

    public void deleteBus(Long id) {
        Bus bus = busRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This bus does not exist"));
        
        busRepository.deleteById(id);
    }

    public void updateBus(Long id, BusDto busDto) {
        Bus busToUpdate = busRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This bus does not exist"));

        busToUpdate.setPlate(busDto.getPlate());
        busToUpdate.setRoute(busDto.getRoute());
        busRepository.save(busToUpdate);
    }
}



