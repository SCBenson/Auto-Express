package com.ct5221.auto_express.controller;

import com.ct5221.auto_express.dto.DealerDTO;
import com.ct5221.auto_express.dto.LoginRequest;
import com.ct5221.auto_express.dto.LoginResponse;
import com.ct5221.auto_express.dto.UserDTO;
import com.ct5221.auto_express.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){
        try {
            UserDTO registeredUser = authService.registerUser(userDTO);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register/dealer")
    public ResponseEntity<?> registerDealer(@RequestBody DealerDTO dealerDTO){
        try {
           DealerDTO registeredDealer = authService.registerDealer(dealerDTO);
           return ResponseEntity.ok(registeredDealer);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        try{
            LoginResponse response = authService.authenticate(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e){
            return ResponseEntity.status(401).build();
        }
    }
}
