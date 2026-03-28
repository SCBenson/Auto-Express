package com.ct5221.auto_express.service;

import com.ct5221.auto_express.domain.Dealer;
import com.ct5221.auto_express.domain.DealerRepository;
import com.ct5221.auto_express.domain.Vehicle;
import com.ct5221.auto_express.domain.VehicleRepository;
import com.ct5221.auto_express.dto.DealerDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DealerService {
    private final DealerRepository dealerRepository;
    private final VehicleRepository vehicleRepository;

    public DealerService(DealerRepository dealerRepository, VehicleRepository vehicleRepository){

        this.dealerRepository = dealerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public DealerDTO createDealer(DealerDTO dealerDTO){
        Dealer dealer = convertToEntity(dealerDTO);
        validateDealer(dealer);
        Dealer savedDealer = dealerRepository.save(dealer);
        return convertToDTO(savedDealer);
    }

    public DealerDTO updateDealer(Long id, DealerDTO dealerDTO) {
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dealer not found with id " + id));

        Dealer dealerDetails = convertToEntity(dealerDTO);
        validateDealer(dealerDetails);

        Optional<Dealer> existingDealer = dealerRepository.findById(dealerDetails.getId());
        if(existingDealer.isPresent() && !existingDealer.get().getId().equals(id)){
            throw new IllegalArgumentException("Dealer with username '" + dealerDetails.getUsername() + "' already exists");
        }

        dealer.setUsername(dealerDetails.getUsername().trim());
        dealer.setEmail(dealerDetails.getEmail().toLowerCase().trim());
        dealer.setPhone(normalizePhone(dealerDetails.getPhone()));
        dealer.setPassword(dealerDetails.getPassword());

        Dealer updatedDealer = dealerRepository.save(dealer);
        return convertToDTO(updatedDealer);
    }

    public List<DealerDTO> getAllDealers() {
        List<Dealer> dealers = (List<Dealer>) dealerRepository.findAll();
        return dealers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<DealerDTO> getDealerById(Long id) {
        return dealerRepository.findById(id)
                .map(this::convertToDTO);
    }
    public Optional<DealerDTO> getDealerByUsername(String username){
        return dealerRepository.findByUsername(username)
                .map(this::convertToDTO);
    }

    public void deleteDealerById(Long id) {
        if(!dealerRepository.existsById(id)) {
            throw new RuntimeException("Dealer not found with id " + id);
        }
        dealerRepository.deleteById(id);
    }

    public Optional<DealerDTO> findByUsername(String username) {
        return dealerRepository.findByUsername(username)
                .map(this::convertToDTO);
    }

    public Optional<DealerDTO> findByEmail(String email) {
        return dealerRepository.findByEmail(email)
                .map(this::convertToDTO);
    }

    public Optional<DealerDTO> findByPhone(String phone) {
        return dealerRepository.findByPhone(phone)
                .map(this::convertToDTO);
    }

    public DealerDTO addVehicleToInventory(Long dealerId, Long vehicleId) {
        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new RuntimeException("Dealer not found"));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        vehicle.setDealer(dealer);
        dealer.getInventory().add(vehicle);
        Dealer savedDealer = dealerRepository.save(dealer);
        return convertToDTO(savedDealer);
    }

    public Optional<DealerDTO> updateContactInfo(Long id, String email, String phone) {
        Optional<Dealer> dealerOpt = dealerRepository.findById(id);
        if(!dealerOpt.isPresent()){
            return Optional.empty();
        }

        Dealer dealer = dealerOpt.get();
        if (email != null && !email.isEmpty()){
            dealer.setEmail(email);
        }
        if(phone != null && !phone.isEmpty()){
            dealer.setPhone(phone);
        }
        Dealer savedDealer = dealerRepository.save(dealer);
        return Optional.of(convertToDTO(savedDealer));
    }

    private void validateDealer(Dealer dealer){
        if(dealer.getUsername() == null || dealer.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("Dealer username is required");
        }

        if(dealer.getEmail() == null || dealer.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("Dealer email is required");
        }

        if(!isValidEmail(dealer.getEmail())){
            throw new IllegalArgumentException("Dealer email is not valid");
        }

        if(dealer.getPhone() == null || dealer.getPhone().trim().isEmpty()){
            throw new IllegalArgumentException("Dealer phone is required");
        }

        if(!isValidPhone(dealer.getPhone())){
            throw new IllegalArgumentException("Dealer phone number is not valid");
        }
    }

    private boolean isValidEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhone(String phone){
        String digitsOnly = phone.replaceAll("[^0-9]", "");
        return digitsOnly.length() >= 10 && digitsOnly.length() <= 15;
    }

    private String normalizePhone(String phone){
        return phone.replaceAll("[^0-9+]", "");
    }

    private DealerDTO convertToDTO(Dealer dealer){
        DealerDTO dto = new DealerDTO();
        dto.setId(dealer.getId());
        dto.setUsername(dealer.getUsername());
        dto.setFirstName(dealer.getFirstName());
        dto.setLastName(dealer.getLastName());
        dto.setEmail(dealer.getEmail());
        dto.setAge(dealer.getAge());
        dto.setPhone(dealer.getPhone());
        dto.setDealershipName(dealer.getDealershipName());
        dto.setLocation(dealer.getLocation());
        return dto;
    }

    private Dealer convertToEntity(DealerDTO dto){
        Dealer dealer = new Dealer();
        dealer.setId(dto.getId());
        dealer.setUsername(dto.getUsername());
        dealer.setFirstName(dto.getFirstName());
        dealer.setLastName(dto.getLastName());
        dealer.setEmail(dto.getEmail());
        dealer.setAge(dto.getAge());
        dealer.setPhone(dto.getPhone());
        dealer.setDealershipName(dto.getDealershipName());
        dealer.setLocation(dto.getLocation());
        dealer.setPassword(dto.getPassword());
        return dealer;
    }
}
