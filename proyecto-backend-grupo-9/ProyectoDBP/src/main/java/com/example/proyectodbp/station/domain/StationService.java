package com.example.proyectodbp.station.domain;

import com.example.proyectodbp.exceptions.ResourceNotFoundException;
import com.example.proyectodbp.station.dto.StationDto;
import com.example.proyectodbp.station.infraestructure.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public String createStation(StationDto stationDto) {
        if (stationRepository.findBystationName(stationDto.getName()).isPresent()) {
            throw new ResourceNotFoundException("This station already exists");
        }

        Station station = new Station();
        station.setName(stationDto.getName());
        station.setRoutes(stationDto.getRoutes());
        stationRepository.save(station);
        return "/station/" + station.getId();
    }

    public StationDto getStationInfo(Long id) {
        Station station = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));

        StationDto stationDto = new StationDto();
        stationDto.setName(station.getName());
        stationDto.setRoutes(station.getRoutes());
        return stationDto;
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    public void updateStation(Long id, StationDto stationDto) {
        Station stationToUpdate = stationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("This station does not exist"));
        stationToUpdate.setName(stationDto.getName());
        stationToUpdate.setRoutes(stationDto.getRoutes());
        stationRepository.save(stationToUpdate);
    }
}
