package com.example.proyectodbp.station.appliaction;

import com.example.proyectodbp.bus.dto.BusDto;
import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.route.dto.RouteDto;
import com.example.proyectodbp.station.domain.StationService;
import com.example.proyectodbp.station.dto.StationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

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

    @GetMapping()
    public ResponseEntity<List<StationDto>> getStations(){
        return ResponseEntity.ok(stationService.getStations());
    }

    @GetMapping("/current/routes")
    public ResponseEntity<List<RouteDto>> getCurrentStationRoutes(){
        return ResponseEntity.ok(stationService.getCurrentStationRoutes());
    }

    @GetMapping("/current/buses")
    public ResponseEntity<List<BusDto>> getCurrentStationBuses(){
        return ResponseEntity.ok(stationService.getCurrentStationBuses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStation(@PathVariable Long id, @RequestBody StationDto station){
        stationService.updateStation(id, station);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id){
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}/passenger")
    public ResponseEntity<Void> removePassenger(@PathVariable Long id, @RequestBody PassengerDto passengerDto){
        stationService.removePassenger(id, passengerDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/passenger")
    public ResponseEntity<Void> addPassenger(@PathVariable Long id, @RequestBody PassengerDto passengerDto){
        stationService.addPassenger(id, passengerDto);
        return ResponseEntity.ok().build();
    }
}