package com.ct5221.auto_express.controller;

import com.ct5221.auto_express.domain.User;
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

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getEmail() == null) {
            throw new ResourceExceptionHandler("Username and email are required");
        }
        User user = convertToEntity(userDTO);
        User created = userService.createUser(user);
        return new ResponseEntity<>(convertToDTO(created), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceExceptionHandler("User not found with id: " + id));
        return ResponseEntity.ok(convertToDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User updated = userService.updateUser(id, user);
        if (updated == null) {
            throw new ResourceExceptionHandler("User not found with id: " + id);
        }
        return ResponseEntity.ok(convertToDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.getUserById(id).isPresent()) {
            throw new ResourceExceptionHandler("User not found with id: " + id);
        }
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/username/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceExceptionHandler("User not found with username: " + username));
        return ResponseEntity.ok(convertToDTO(user));
    }

    @GetMapping("/search/email/{email}")
    public ResponseEntity<UserDTO> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new ResourceExceptionHandler("User not found with email: " + email));
        return ResponseEntity.ok(convertToDTO(user));
    }

    @GetMapping("/search/phone/{phone}")
    public ResponseEntity<UserDTO> findByPhone(@PathVariable String phone) {
        User user = userService.findByPhone(phone)
                .orElseThrow(() -> new ResourceExceptionHandler("User not found with phone: " + phone));
        return ResponseEntity.ok(convertToDTO(user));
    }

    @GetMapping("/search/username-contains/{keyword}")
    public ResponseEntity<List<UserDTO>> findByUsernameContains(@PathVariable String keyword) {
        List<UserDTO> users = userService.getAllUsers()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/purchase-vehicle/{vehicleId}")
    public ResponseEntity<UserDTO> purchaseVehicle(@PathVariable Long userId, @PathVariable Long vehicleId) {
        User user = userService.purchaseVehicle(userId, vehicleId);
        if (user == null) {
            throw new ResourceExceptionHandler("User not found with id: " + userId);
        }
        return ResponseEntity.ok(convertToDTO(user));
    }

    @PutMapping("/{id}/update-password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody String newPassword) {
        if (newPassword == null || newPassword.length() < 6) {
            throw new ResourceExceptionHandler("Password must be at least 6 characters long");
        }
        userService.updatePassword(id, newPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        dto.setPhone(user.getPhone());
        dto.setPassword(user.getPassword());
        return dto;
    }

    private User convertToEntity(UserDTO dto) {
        return new User(dto.getUsername(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getAge(), dto.getPhone(), dto.getPassword());
    }
}