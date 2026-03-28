package com.ct5221.auto_express.service;


import com.ct5221.auto_express.domain.Dealer;
import com.ct5221.auto_express.domain.DealerRepository;
import com.ct5221.auto_express.domain.Vehicle;
import com.ct5221.auto_express.domain.VehicleRepository;
import com.ct5221.auto_express.dto.VehicleDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final DealerRepository dealerRepository;

    public VehicleService(VehicleRepository vehicleRepository, DealerRepository dealerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.dealerRepository = dealerRepository;
    }

    public VehicleDTO createVehicle(VehicleDTO vehicleDTO){
        Vehicle vehicle = new Vehicle();

        vehicle.setMake(vehicleDTO.getMake());
        vehicle.setModel(vehicleDTO.getModel());
        vehicle.setYear(vehicleDTO.getYear());
        vehicle.setPrice(vehicleDTO.getPrice());
        vehicle.setColor(vehicleDTO.getColor());
        vehicle.setMileage(vehicleDTO.getMileage());
        vehicle.setAvailable(vehicleDTO.getAvailable());

        if(vehicleDTO.getDealerId() != null) {
            Dealer dealer = dealerRepository.findById(vehicleDTO.getDealerId())
                    .orElseThrow(() -> new RuntimeException("Dealer not found with id " + vehicleDTO.getDealerId()));
            vehicle.setDealer(dealer);
        }

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(savedVehicle);
    }

    public VehicleDTO updateVehicle(Long id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found with id " + id));

        Vehicle vehicleDetails = convertToEntity(vehicleDTO);
        validateVehicle(vehicleDetails);

        vehicle.setMake(normalize(vehicleDetails.getMake()));
        vehicle.setModel(normalize(vehicleDetails.getModel()));
        vehicle.setColor(vehicleDetails.getColor());
        vehicle.setYear(vehicleDetails.getYear());
        vehicle.setMileage(vehicleDetails.getMileage());
        vehicle.setPrice(vehicleDetails.getPrice());
        vehicle.setAvailable(vehicleDetails.getAvailable());

        if(vehicleDTO.getDealerId() != null) {
            Dealer dealer = dealerRepository.findById(vehicleDTO.getDealerId())
                    .orElseThrow(() -> new RuntimeException("Dealer not found with id " + vehicleDTO.getDealerId()));
            vehicle.setDealer(dealer);
        }

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(updatedVehicle);
    }

    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = (List<Vehicle>) vehicleRepository.findAll();
        return vehicles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<VehicleDTO> getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<VehicleDTO> getVehiclesByDealerId(Long dealerId) {
        if(!dealerRepository.existsById(dealerId)) {
            throw new RuntimeException("Dealer not found with id " + dealerId);
        }
        List<Vehicle> vehicles = vehicleRepository.findByDealerId(dealerId);
        return vehicles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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

    private VehicleDTO convertToDTO(Vehicle vehicle){
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setColor(vehicle.getColor());
        dto.setYear(vehicle.getYear());
        dto.setMileage(vehicle.getMileage());
        dto.setPrice(vehicle.getPrice());
        dto.setAvailable(vehicle.getAvailable());
        if(vehicle.getDealer() != null) {
            dto.setDealerId(vehicle.getDealer().getId());
        }
        return dto;
    }

    private Vehicle convertToEntity(VehicleDTO dto){
        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setColor(dto.getColor());
        vehicle.setYear(dto.getYear());
        vehicle.setMileage(dto.getMileage());
        vehicle.setPrice(dto.getPrice());
        vehicle.setAvailable(dto.getAvailable());
        return vehicle;
    }
}
