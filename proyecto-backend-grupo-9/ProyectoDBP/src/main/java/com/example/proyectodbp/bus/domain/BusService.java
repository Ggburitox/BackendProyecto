package com.example.proyectodbp.bus.domain;

import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.exceptions.EntityAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public void createBus(Bus newBus) {
        busRepository.save(newBus);
    }

    public Bus getBus(Long id) {
        return busRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This bus does not exist"));
    }

    public void deleteBus(Long id) {
        busRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This bus does not exist"));

        busRepository.deleteById(id);
    }

    public Bus updateBus(Long id, Bus bus) {
        Bus busToUpdate = busRepository
                .findById(id)
                .orElseThrow(() -> new EntityAlreadyExists("This bus does not exist"));

        busToUpdate.setPlaca(bus.getPlaca());
        busToUpdate.setDriver(bus.getDriver());
        busToUpdate.setRoute_act(bus.getRoute_act());
        busRepository.save(busToUpdate);

        return busToUpdate;
    }

}



