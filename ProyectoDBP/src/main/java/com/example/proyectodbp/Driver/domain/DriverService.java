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
//    public void saveDriver(Driver driver) {
//        driverRepository.save(driver);
//    }
//
//    public void deleteDriver(Long id) {
//        driverRepository.deleteById(id);
//    }
//
//    public void updateDriver(Long id, Driver driver) {
//
//        Driver driverToUpdate = driverRepository
//                .findById(id)
//                .orElseThrow(() -> new RuntimeException("Driver not found"));
//
//        driverToUpdate.setFirstName(driver.getFirstName());
//        driverToUpdate.setLastName(driver.getLastName());
//        driverToUpdate.setTrips(driver.getTrips());
//        driverToUpdate.setAvgRating(driver.getAvgRating());
//        driverToUpdate.setCategory(driver.getCategory());
//        driverToUpdate.setVehicle(driver.getVehicle());
//        driverToUpdate.setCoordinate(driver.getCoordinate());
//
//        driverRepository.save(driverToUpdate);
//    }
//
//    public void updateDriverLocation(Long id, Double latitude, Double longitude) {
//        Driver driver = driverRepository
//                .findById(id)
//                .orElseThrow(() -> new RuntimeException("Driver not found"));
//
//        Coordinate coordinate = new Coordinate();
//        coordinate.setLatitude(latitude);
//        coordinate.setLongitude(longitude);
//        coordinateRepository.save(coordinate);
//        driver.setCoordinate(coordinate);
//        driverRepository.save(driver);
//    }
//
//    public void updateDriverCar(Long id, Vehicle vehicle) {
//        Driver driver = driverRepository
//                .findById(id)
//                .orElseThrow(() -> new RuntimeException("Driver not found"));
//
//        driver.setVehicle(vehicle);
//        driverRepository.save(driver);
//    }
}
