package com.example.proyectodbp.station.appliaction;

import com.example.proyectodbp.station.domain.StationService;
import com.example.proyectodbp.station.dto.StationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping
    public ResponseEntity<Void> createStation(@RequestBody StationDto stationDto){
        return ResponseEntity.created(URI.create(stationService.createStation(stationDto))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationDto> getStation(@PathVariable Long id){
        return ResponseEntity.ok(stationService.getStationInfo(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateStation(@PathVariable Long id, @RequestBody StationDto station){
        stationService.updateStation(id, station);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id){
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

}
