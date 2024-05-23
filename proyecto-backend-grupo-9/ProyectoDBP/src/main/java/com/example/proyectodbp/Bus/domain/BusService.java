package com.example.proyectodbp.Bus.domain;

import com.example.proyectodbp.Bus.infraestructure.BusRepository;
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
                .orElseThrow(() -> new RuntimeException("Bus not found"));
    }

    public void deleteBus(Long id) {
        busRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        busRepository.deleteById(id);
    }

    public Bus updateBus(Long id, Bus bus) {
        Bus busToUpdate = busRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        busToUpdate.setPlaca(bus.getPlaca());
        busToUpdate.setDriver(bus.getDriver());
        busToUpdate.setRoute_act(bus.getRoute_act());
        busRepository.save(busToUpdate);

        return busToUpdate;
    }

}



