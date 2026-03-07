package com.ct5221.auto_express.controller;

import com.ct5221.auto_express.domain.Vehicle;

import com.ct5221.auto_express.dto.VehicleDTO;
import com.ct5221.auto_express.exception.BadRequestException;
import com.ct5221.auto_express.exception.ResourceExceptionHandler;
import com.ct5221.auto_express.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleDTO> createVehicle(@RequestBody VehicleDTO vehicleDTO) {
        if(vehicleDTO.getMake() == null || vehicleDTO.getModel() == null) {
            throw new BadRequestException("Make a model are required");
        }
        Vehicle vehicle = convertToEntity(vehicleDTO);
        Vehicle created = vehicleService.createVehicle(vehicle);
        return new ResponseEntity<>(convertToDTO(created), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAllVehicles(){
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id){
        Vehicle vehicle = vehicleService.getVehicleById(id)
                .orElseThrow(() -> new ResourceExceptionHandler("Vehicle not found with id"));
        return ResponseEntity.ok(convertToDTO(vehicle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @RequestBody VehicleDTO vehicleDTO){
        Vehicle vehicle = convertToEntity(vehicleDTO);
        Vehicle updated = vehicleService.updateVehicle(id, vehicle);
        if(updated == null){
            throw new ResourceExceptionHandler("Vehicle not found with id: " + id);
        }
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id){
        if(!vehicleService.getVehicleById(id).isPresent()){
            throw new ResourceExceptionHandler("Vehicle not found with id: " + id);
        }
        vehicleService.deleteVehicleById(id);
        return ResponseEntity.noContent().build();
    }

    private VehicleDTO convertToDTO(Vehicle vehicle){
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setYear(vehicle.getYear());
        dto.setPrice(vehicle.getPrice());
        return dto;
    }

    private Vehicle convertToEntity(VehicleDTO dto){
        return new Vehicle(dto.getMake(), dto.getModel(), dto.getYear(), dto.getPrice());
    }
}
