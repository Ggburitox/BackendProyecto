package com.example.proyectodbp.station.appliaction;

import com.example.proyectodbp.passenger.dto.NewPassengerRequestDto;
import com.example.proyectodbp.station.domain.StationService;
import com.example.proyectodbp.station.dto.NewStationRequestDto;
import com.example.proyectodbp.station.dto.StationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@Controller
@RequestMapping("/station")
public class StationController {
    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @PostMapping
    public ResponseEntity<Void> createStation(@RequestBody StationDto stationDto){
        return ResponseEntity.created(URI.create(stationService.createStation(stationDto))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StationDto> getStation(@PathVariable Long id){
        return ResponseEntity.ok(stationService.getStationInfo(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStation(@PathVariable Long id, @RequestBody NewStationRequestDto station){
        stationService.updateStation(id, station);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id){
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/passenger")
    public ResponseEntity<Void> addPassenger(@PathVariable Long id, @RequestBody NewPassengerRequestDto passengerDto){
        stationService.addPassenger(id, passengerDto);
        return ResponseEntity.ok().build();
    }

}
