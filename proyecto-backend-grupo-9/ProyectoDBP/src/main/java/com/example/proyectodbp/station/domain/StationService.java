package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.station.infraestructure.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public void createStation(Station newStation) {
        stationRepository.save(newStation);
    }

    public Station getStation(Long id) {
        return stationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("station not found"));
    }

    public void deleteStation(Long id) {
        stationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("station not found"));

        stationRepository.deleteById(id);
    }

    public Station updateStation(Long id, Station station) {
        Station stationToUpdate = stationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("station not found"));

        stationToUpdate.setStation_name(station.getStation_name());
        stationToUpdate.setRoutes(station.getRoutes());
        stationRepository.save(stationToUpdate);

        return stationToUpdate;
    }

}
