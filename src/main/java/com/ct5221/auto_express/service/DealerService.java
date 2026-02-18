package com.ct5221.auto_express.service;

import com.ct5221.auto_express.domain.Dealer;
import com.ct5221.auto_express.domain.DealerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DealerService {
    private final DealerRepository dealerRepository;

    public DealerService(DealerRepository dealerRepository){
        this.dealerRepository = dealerRepository;
    }

    public Optional<Dealer> getDealerById(Long id) {
        return dealerRepository.findById(id);
    }

    public List<Dealer> getAllDealers() {
        return (List<Dealer>) dealerRepository.findAll();
    }

    public Dealer createDealer(Dealer dealer){
        validateDealer(dealer);
        return dealerRepository.save(dealer);
    }

    public void deleteDealerById(Long id) {
        if(!dealerRepository.existsById(id)) {
            throw new RuntimeException("Dealer not found with id " + id);
        }
        dealerRepository.deleteById(id);
    }

    public Dealer updateDealer(Long id, Dealer dealerDetails) {
        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dealer not found with id " + id));

        validateDealer(dealerDetails);

        Optional<Dealer> existingDealer = dealerRepository.findById(dealerDetails.getId());
        if(existingDealer.isPresent() && !existingDealer.get().getId().equals(id)){
            throw new IllegalArgumentException("Dealer with username '" + dealerDetails.getUsername() + "' already exists");
        }

        dealer.setUsername(dealerDetails.getUsername().trim());
        dealer.setEmail(dealerDetails.getEmail().toLowerCase().trim());
        dealer.setPhone(normalizePhone(dealerDetails.getPhone()));
        dealer.setPassword(dealerDetails.getPassword());

        return dealerRepository.save(dealer);
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

    public Optional<Dealer> getDealerByUsername(String username){
        return dealerRepository.findByUsername(username);
    }

    public List<Dealer> searchDealers(String searchTerm) {
        return dealerRepository.findByUsernameContainingIgnoreCase(searchTerm);
    }
}
