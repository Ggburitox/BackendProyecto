package com.example.proyectodbp.bus.domain;

import com.example.proyectodbp.bus.infraestructure.BusRepository;
import com.example.proyectodbp.exceptions.UniqueResourceAlreadyExist;
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
                .orElseThrow(() -> new UniqueResourceAlreadyExist("This bus does not exist"));
    }

    public void deleteBus(Long id) {
        busRepository
                .findById(id)
                .orElseThrow(() -> new UniqueResourceAlreadyExist("This bus does not exist"));

        busRepository.deleteById(id);
    }

    public Bus updateBus(Long id, Bus bus) {
        Bus busToUpdate = busRepository
                .findById(id)
                .orElseThrow(() -> new UniqueResourceAlreadyExist("This bus does not exist"));

        busToUpdate.setPlate(bus.getPlate());
        busToUpdate.setDriver(bus.getDriver());
        busToUpdate.setRoute(bus.getRoute());
        busRepository.save(busToUpdate);

        return busToUpdate;
    }

}



