package com.example.proyectodbp.Station.appliaction;

import com.example.proyectodbp.Route.domain.Route;
import com.example.proyectodbp.Station.domain.Station;
import com.example.proyectodbp.Station.domain.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping
    public ResponseEntity<Route> createStation(@RequestBody Station station){
        stationService.createStation(station);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Station> getStation(@PathVariable Long id){
        return ResponseEntity.ok(stationService.getStation(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Station> updateStation(@PathVariable Long id, @RequestBody Station station){
        return ResponseEntity.ok(stationService.updateStation(id, station));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id){
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

}
