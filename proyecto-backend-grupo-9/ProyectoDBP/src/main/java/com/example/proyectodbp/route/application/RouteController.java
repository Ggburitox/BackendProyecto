package com.example.proyectodbp.route.application;

import com.example.proyectodbp.passenger.dto.PassengerDto;
import com.example.proyectodbp.route.domain.Route;
import com.example.proyectodbp.route.domain.RouteService;
import com.example.proyectodbp.route.dto.RouteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/routes")
public class RouteController {
    @Autowired
    private RouteService routeService;

    @PostMapping()
    public ResponseEntity<Void> createDriver(@RequestBody RouteDto routeDto) {
        return ResponseEntity.created(URI.create(routeService.createRoute(routeDto))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> getRoute(@PathVariable Long id) {
        return new ResponseEntity<>(routeService.getRoute(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRoute(@PathVariable Long id, @RequestBody RouteDto route) {
        routeService.updateRoute(id, route);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

}
