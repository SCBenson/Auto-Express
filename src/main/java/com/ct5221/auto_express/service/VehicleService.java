package com.ct5221.auto_express.service;


import com.ct5221.auto_express.domain.Vehicle;
import com.ct5221.auto_express.domain.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle createVehicle(Vehicle vehicle){
        validateVehicle(vehicle);

        vehicle.setMake(normalize(vehicle.getMake()));
        vehicle.setModel(normalize(vehicle.getModel()));

        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle vehicleDetails) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id " + id));

        validateVehicle(vehicleDetails);

        vehicle.setMake(normalize(vehicleDetails.getMake()));
        vehicle.setModel(normalize(vehicleDetails.getModel()));
        vehicle.setColor(vehicleDetails.getColor());
        vehicle.setYear(vehicleDetails.getYear());
        vehicle.setMileage(vehicleDetails.getMileage());
        vehicle.setPrice(vehicleDetails.getPrice());
        vehicle.setAvailable(vehicleDetails.getAvailable());

        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicleById(Long id) {
        if(!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found with id " + id);
        }
        vehicleRepository.deleteById(id);
    }
    private void validateVehicle(Vehicle vehicle){
        if(vehicle.getMake() == null || vehicle.getMake().trim().isEmpty()){
            throw new IllegalArgumentException("Vehicle make is required");
        }
        if(vehicle.getModel() == null || vehicle.getModel().trim().isEmpty()){
            throw new IllegalArgumentException("Vehicle model is required");
        }
        int currentYear = Year.now().getValue();
        if(vehicle.getYear() < 1886 || vehicle.getYear() > currentYear + 1){
            throw new IllegalArgumentException("Vehicle year must be between 1886 and " + (currentYear + 1));
        }
        if(vehicle.getPrice() == null){
            throw new IllegalArgumentException("Vehicle price is required");
        }
        if(vehicle.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Vehicle price cannot be negative");
        }
    }
    private String normalize(String value){
        return value == null ? null : value.trim();
    }
}
