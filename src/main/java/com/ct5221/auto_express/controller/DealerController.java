package com.ct5221.auto_express.controller;

import com.ct5221.auto_express.domain.Dealer;
import com.ct5221.auto_express.domain.DealerRepository;
import com.ct5221.auto_express.dto.DealerDTO;
import com.ct5221.auto_express.exception.ResourceExceptionHandler;
import com.ct5221.auto_express.service.DealerService;
import com.ct5221.auto_express.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dealers")

public class DealerController {
    @Autowired
    private DealerService dealerService;

    @PostMapping
    public ResponseEntity<DealerDTO> createDealer(@RequestBody DealerDTO dealerDTO){
        if(dealerDTO.getUsername() == null || dealerDTO.getEmail() == null) {
            throw new BadRequestException("Username and email are required");
        }
        Dealer dealer = convertToEntity(dealerDTO);
        Dealer created = dealerService.createDealer(dealer);
        return new ResponseEntity<>(convertToDTO(created), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<DealerDTO>> getAllDealers(){
        List<DealerDTO> dealers = dealerService.getAllDealers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dealers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerDTO> getDealerById(@PathVariable Long id){
        Dealer dealer = dealerService.getDealerById(id)
                .orElseThrow(() -> new ResourceExceptionHandler("Dealer not found with id: " + id));
        return ResponseEntity.ok(convertToDTO(dealer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealerDTO> updateDealer(@PathVariable Long id, @RequestBody DealerDTO dealerDTO){
        Dealer dealer = convertToEntity(dealerDTO);
        Dealer updated = dealerService.updateDealer(id, dealer);
        if(updated == null) {
            throw new ResourceExceptionHandler("Dealer not found with id: " + id);
        }
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDealer(@PathVariable Long id){
        if (!dealerService.getDealerById(id).isPresent()){
            throw new ResourceExceptionHandler("Dealer not found with id: " + id);
        }
        dealerService.deleteDealerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/username/{username}")
    public ResponseEntity<DealerDTO> findByUsername(@PathVariable String username){
        Dealer dealer = dealerService.findByUsername(username)
                .orElseThrow(() -> new ResourceExceptionHandler("Dealer not found with username: " + username));
        return ResponseEntity.ok(convertToDTO(dealer));
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<DealerDTO> findByEmail(@PathVariable String email){
        Dealer dealer = dealerService.findByEmail(email)
                .orElseThrow(() -> new ResourceExceptionHandler("Dealer not found with email: " + email));
        return ResponseEntity.ok(convertToDTO(dealer));
    }

    @GetMapping("/search/phone/{phone}")
    public ResponseEntity<DealerDTO> findByPhone(@PathVariable String phone){
        Dealer dealer = dealerService.findByPhone(phone)
                .orElseThrow(() -> new ResourceExceptionHandler("Dealer not found with phone: " + phone));
        return ResponseEntity.ok(convertToDTO(dealer));
    }

    @PutMapping("/{dealerId}/add-vehicle/{vehicleId}")
    public ResponseEntity<DealerDTO> addVehicleToInventory(@PathVariable Long dealerId, @PathVariable Long vehicleId){
        Dealer updated = dealerService.addVehicleToInventory(dealerId, vehicleId);
        if(updated == null) {
            throw new ResourceExceptionHandler("Dealer or Vehicle not found with id: " + dealerId + ", " + vehicleId);
        }
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @PutMapping("/{id}/update-contact")
    public ResponseEntity<DealerDTO> updateContactInfo(@PathVariable Long id, @RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        Dealer dealer = dealerService.updateContactInfo(id, email, phone);
        if(dealer == null) {
            throw new ResourceExceptionHandler("Dealer not found with id: " + id);
        }
        return ResponseEntity.ok(convertToDTO(dealer));
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
        dto.setPassword(dealer.getPassword());
        return dto;
    }

    private Dealer convertToEntity(DealerDTO dto){
        return new Dealer(dto.getUsername(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getAge(), dto.getPhone(), dto.getPassword());
    }



}
