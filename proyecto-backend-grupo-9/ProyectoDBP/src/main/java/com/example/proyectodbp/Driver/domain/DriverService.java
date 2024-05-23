package com.example.proyectodbp.Driver.domain;

import com.example.proyectodbp.Driver.infraestructure.DriverRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    public Driver getDriver(Long id) {
        return driverRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }
//
//    public void saveDriver(Driver Driver) {
//        driverRepository.save(Driver);
//    }
//
//    public void deleteDriver(Long id) {
//        driverRepository.deleteById(id);
//    }
//
//    public void updateDriver(Long id, Driver Driver) {
//
//        Driver driverToUpdate = driverRepository
//                .findById(id)
//                .orElseThrow(() -> new RuntimeException("Driver not found"));
//
//        driverToUpdate.setFirstName(Driver.getFirstName());
//        driverToUpdate.setLastName(Driver.getLastName());
//        driverToUpdate.setTrips(Driver.getTrips());
//        driverToUpdate.setAvgRating(Driver.getAvgRating());
//        driverToUpdate.setCategory(Driver.getCategory());
//        driverToUpdate.setVehicle(Driver.getVehicle());
//        driverToUpdate.setCoordinate(Driver.getCoordinate());
//
//        driverRepository.save(driverToUpdate);
//    }
//
//    public void updateDriverLocation(Long id, Double latitude, Double longitude) {
//        Driver Driver = driverRepository
//                .findById(id)
//                .orElseThrow(() -> new RuntimeException("Driver not found"));
//
//        Coordinate coordinate = new Coordinate();
//        coordinate.setLatitude(latitude);
//        coordinate.setLongitude(longitude);
//        coordinateRepository.save(coordinate);
//        Driver.setCoordinate(coordinate);
//        driverRepository.save(Driver);
//    }
//
//    public void updateDriverCar(Long id, Vehicle vehicle) {
//        Driver Driver = driverRepository
//                .findById(id)
//                .orElseThrow(() -> new RuntimeException("Driver not found"));
//
//        Driver.setVehicle(vehicle);
//        driverRepository.save(Driver);
//    }
}
