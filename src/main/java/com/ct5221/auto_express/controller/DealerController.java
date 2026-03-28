package com.ct5221.auto_express.controller;

import com.ct5221.auto_express.dto.DealerDTO;
import com.ct5221.auto_express.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealers")
@CrossOrigin(origins = "http://localhost:5173")
public class DealerController {
    @Autowired
    private DealerService dealerService;

    @PostMapping
    public ResponseEntity<DealerDTO> createDealer(@RequestBody DealerDTO dealerDTO){
        DealerDTO createdDealer = dealerService.createDealer(dealerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDealer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DealerDTO> updateDealer(@PathVariable Long id, @RequestBody DealerDTO dealerDTO){
        DealerDTO updatedDealer = dealerService.updateDealer(id, dealerDTO);
        return ResponseEntity.ok(updatedDealer);
    }

    @GetMapping
    public ResponseEntity<List<DealerDTO>> getAllDealers(){
        List<DealerDTO> dealers = dealerService.getAllDealers();
        return ResponseEntity.ok(dealers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerDTO> getDealerById(@PathVariable Long id){
        return dealerService.getDealerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{username}")
    public ResponseEntity<DealerDTO> getDealerByUsername(@PathVariable String username){
        return dealerService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDealer(@PathVariable Long id){
        dealerService.deleteDealerById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/username/{username}")
    public ResponseEntity<DealerDTO> findByUsername(@PathVariable String username){
        return dealerService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<DealerDTO> findByEmail(@PathVariable String email){
        return dealerService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/phone/{phone}")
    public ResponseEntity<DealerDTO> findByPhone(@PathVariable String phone){
        return dealerService.findByPhone(phone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{dealerId}/add-vehicle/{vehicleId}")
    public ResponseEntity<DealerDTO> addVehicleToInventory(@PathVariable Long dealerId, @PathVariable Long vehicleId){
        DealerDTO dealer = dealerService.addVehicleToInventory(dealerId, vehicleId);
        return ResponseEntity.ok(dealer);
    }

    @PutMapping("/{id}/update-contact")
    public ResponseEntity<DealerDTO> updateContactInfo(@PathVariable Long id, @RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        return dealerService.updateContactInfo(id, email, phone)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
