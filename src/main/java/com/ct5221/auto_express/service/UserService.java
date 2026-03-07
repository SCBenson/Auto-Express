package com.ct5221.auto_express.service;

import com.ct5221.auto_express.domain.User;
import com.ct5221.auto_express.domain.UserRepository;
import com.ct5221.auto_express.domain.VehicleRepository;
import com.ct5221.auto_express.domain.Vehicle;
import com.ct5221.auto_express.exception.ResourceExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    private VehicleRepository vehicleRepository;

    public UserService(UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public List<User> findByUsernameContains(String keyword){
        return userRepository.findByUsernameContains(keyword);
    }

    public User purchaseVehicle(Long userId, Long vehicleId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) return null;
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);
        if(vehicle == null) return null;

        user.getPurchasedVehicles().add(vehicle);
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){ return (List<User>) userRepository.findAll(); }

    public User createUser(User user) {

        validateUser(user);
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setPassword(userDetails.getPassword());

        return userRepository.save(user);
    }

    public void updatePassword(Long id, String newPassword){
        userRepository.findById(id).ifPresent(user -> {
            user.setPassword(newPassword);
            userRepository.save(user);
        });
    }

    public void deleteUserById(Long id) {
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
}
