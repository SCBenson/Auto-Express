package com.ct5221.auto_express.service;

import com.ct5221.auto_express.domain.User;
import com.ct5221.auto_express.domain.UserRepository;
import com.ct5221.auto_express.domain.VehicleRepository;
import com.ct5221.auto_express.domain.Vehicle;
import com.ct5221.auto_express.dto.UserDTO;
import com.ct5221.auto_express.exception.BadRequestException;
import com.ct5221.auto_express.exception.ResourceExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    private VehicleRepository vehicleRepository;

    public UserService(UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        // Validation
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists: " + userDTO.getEmail());
        }

        // Business logic
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword()); // In real app, hash with BCrypt!
        user.setAge(userDTO.getAge());
        user.setPhone(userDTO.getPhone());
        user.setLocation(userDTO.getLocation()); // In real app, hash this!

        User saved = userRepository.save(user);
        return convertToDTO(saved);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found with id: " + id));
        return convertToDTO(user);
    }

    public UserDTO findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found with username: " + username));
        return convertToDTO(user);
    }
    public UserDTO findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found with email: " + email));
        return convertToDTO(user);
    }

    public UserDTO findByPhone(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new BadRequestException("User not found with phone: " + phone));
        return convertToDTO(user);
    }

    public UserDTO purchaseVehicle(Long userId, Long vehicleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException("User not found with id: " + userId));


        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new BadRequestException("Vehicle not found with id: " + vehicleId));

        if(!vehicle.getAvailable()){
            throw new BadRequestException("Vehicle with id " + vehicleId + " is not available for purchase");
        }

        user.getPurchasedVehicles().add(vehicle);
        vehicle.setAvailable(false);

        vehicleRepository.save(vehicle);
        User updated = userRepository.save(user);

        return convertToDTO(updated);
    }

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        if (!user.getEmail().equals(userDTO.getEmail())) {
            userRepository.findByEmail(userDTO.getEmail()).ifPresent(u -> {
                throw new BadRequestException("Email already exists: " + userDTO.getEmail());
            });
        }

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(userDTO.getPassword());

        User updated = userRepository.save(user);

        return convertToDTO(updated);
    }

    public void updatePassword(Long id, String newPassword) {
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new BadRequestException("Password cannot be empty");
        }

        if (newPassword.length() < 6) {
            throw new BadRequestException("Password must be at least 6 characters long");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found with id: " + id));

        user.setPassword(newPassword); // In production, hash with BCrypt!
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

    private void validateUser(User user){
        if(user.getUsername() == null || user.getUsername().trim().isEmpty()){
            throw new IllegalArgumentException("User username is required");
        }

        if(user.getEmail() == null || user.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("User email is required");
        }

        if(!isValidEmail(user.getEmail())){
            throw new IllegalArgumentException("User email is not valid");
        }

        if(user.getPhone() == null || user.getPhone().trim().isEmpty()){
            throw new IllegalArgumentException("User phone is required");
        }

        if(!isValidPhone(user.getPhone())){
            throw new IllegalArgumentException("User phone number is not valid");
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

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> searchUsers(String searchTerm) {
        return userRepository.findByUsernameContainingIgnoreCase(searchTerm);
    }

    public List<Vehicle> getPurchasedVehicles(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceExceptionHandler("User not found with id: " + userId));
        return new ArrayList<>(user.getPurchasedVehicles());
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
        dto.setLocation(user.getLocation());
        return dto;
    }
}
