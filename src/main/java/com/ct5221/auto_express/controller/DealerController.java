package com.ct5221.auto_express.controller;

import com.ct5221.auto_express.domain.Dealer;
import com.ct5221.auto_express.domain.DealerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dealers")

public class DealerController {
    @Autowired
    private DealerRepository dealerRepository;

    @PostMapping
    public Dealer createDealer(@RequestBody Dealer dealer){
        return dealerRepository.save(dealer);
    }

    @GetMapping
    public List<Dealer> getAllDealers(){
        return dealerRepository.findAll();
    }
}
