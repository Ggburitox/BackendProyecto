package com.example.proyectodbp.station.appliaction;

import com.example.proyectodbp.station.domain.Station;
import com.example.proyectodbp.station.domain.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @PostMapping
    public ResponseEntity<Void> createStation(@RequestBody Station station){
        stationService.createStation(station);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Station> getStation(@PathVariable Long id){
        return new ResponseEntity<>(stationService.getStation(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateStation(@PathVariable Long id, @RequestBody Station station){
        stationService.updateStation(id, station);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(@PathVariable Long id){
        stationService.deleteStation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
