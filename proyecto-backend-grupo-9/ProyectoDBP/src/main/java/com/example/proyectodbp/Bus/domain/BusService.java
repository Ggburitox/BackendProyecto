package com.example.proyectodbp.Bus.domain;

import com.example.proyectodbp.Bus.infraestructure.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusService {
    @Autowired
    private BusRepository busRepository;

    public void createBus(Bus newBus, long busId) {
        Bus bus = busRepository
                .findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        newBus.setPlaca(bus.getPlaca());
        newBus.setDriver(bus.getDriver());
        newBus.setRoute_act(bus.getRoute_act());
        busRepository.save(newBus);
    }
}
//
//    public void deleteReview(Long id) {
//        reviewRepository
//                .findById(id)
//                .orElseThrow(() -> new RuntimeException("Review not found"));
//
//        reviewRepository.deleteById(id);
//    }


