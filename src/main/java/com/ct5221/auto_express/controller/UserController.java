package com.ct5221.auto_express.controller;

import com.ct5221.auto_express.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import com.ct5221.auto_express.dto.UserDTO;
import com.ct5221.auto_express.exception.ResourceExceptionHandler;
import com.ct5221.auto_express.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/search/username/{username}")
    public UserDTO findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/search/email/{email}")
    public UserDTO findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @GetMapping("/search/phone/{phone}")
    public UserDTO findByPhone(@PathVariable String phone) {
        return userService.findByPhone(phone);
    }

    @PutMapping("/{userId}/purchase-vehicle/{vehicleId}")
    public UserDTO purchaseVehicle(@PathVariable Long userId, @PathVariable Long vehicleId) {
        return userService.purchaseVehicle(userId, vehicleId);
    }

    @PutMapping("/{id}/update-password")
    public void updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        userService.updatePassword(id, newPassword);
    }

    private User convertToEntity(UserDTO dto) {
        return new User(dto.getFirstName(), dto.getLastName(), dto.getUsername(), dto.getEmail(), dto.getAge(), dto.getPhone(), dto.getPassword(), dto.getLocation());
    }
}