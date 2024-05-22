package com.example.proyectodbp.Bus.application;

import com.example.proyectodbp.Bus.domain.Bus;
import com.example.proyectodbp.Bus.domain.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bus")
public class BusController {
    @Autowired
    private BusService busService;

    @PostMapping()
    public void createBus(@RequestBody Bus bus, @RequestParam long busId) {
        busService.createBus(bus, busId);
    }

}
