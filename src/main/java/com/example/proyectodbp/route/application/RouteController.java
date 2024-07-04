package com.example.proyectodbp.route.application;

import com.example.proyectodbp.route.domain.RouteService;
import com.example.proyectodbp.route.dto.NewRouteRequestDto;
import com.example.proyectodbp.route.dto.RouteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@Controller
@RequestMapping("/routes")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping()
    public ResponseEntity<Void> createDriver(@RequestBody NewRouteRequestDto routeDto) {
        return ResponseEntity.created(URI.create(routeService.createRoute(routeDto))).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.getRouteInfo(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRoute(@PathVariable Long id, @RequestBody RouteDto route) {
        routeService.updateRoute(id, route);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/station")
    public ResponseEntity<Void> addStation(@PathVariable Long id, @RequestBody String stationName) {
        routeService.addStation(id, stationName);
        return ResponseEntity.ok().build();
    }
}