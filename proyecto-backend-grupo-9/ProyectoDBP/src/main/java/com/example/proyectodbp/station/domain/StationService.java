package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public String createStation(StationDto newStation) {
        if (stationRepository.findByStation_name(newStation.getStation_name()).isPresent()) {
            throw new RuntimeException("This station already exists");
        }

        Station station = new Station();
        station.setStation_name(newStation.getStation_name());
        station.setRoutes(newStation.getRoutes());
        stationRepository.save(station);
        return "/station/" + station.getId();
    }

    public StationDto getStation(Long id) {
        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("This station does not exist"));

        StationDto stationDto = new StationDto();
        stationDto.setStation_name(station.getStation_name());
        stationDto.setRoutes(station.getRoutes());
        return stationDto;
    }

    public void deleteStation(Long id) {
        stationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("This station does not exist"));

        stationRepository.deleteById(id);
    }

    public void updateStation(Long id, StationDto stationDto) {
        stationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("This station does not exist"));

        Station stationToUpdate = new Station();
        stationToUpdate.setStation_name(stationDto.getStation_name());
        stationToUpdate.setRoutes(stationDto.getRoutes());
        stationRepository.save(stationToUpdate);
    }

}
