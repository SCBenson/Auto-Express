package com.ct5221.auto_express.service;

import com.ct5221.auto_express.domain.Dealer;
import com.ct5221.auto_express.domain.DealerRepository;
import com.ct5221.auto_express.domain.User;
import com.ct5221.auto_express.domain.UserRepository;
import com.ct5221.auto_express.dto.DealerDTO;
import com.ct5221.auto_express.dto.LoginRequest;
import com.ct5221.auto_express.dto.LoginResponse;
import com.ct5221.auto_express.dto.UserDTO;
import com.ct5221.auto_express.security.Authenticatable;
import com.ct5221.auto_express.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public UserDTO registerUser(UserDTO userDTO){
        if(userRepository.findByEmail(userDTO.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email is already registered.");
        }

        // Validate password BEFORE encoding
        String rawPassword = userDTO.getPassword();
        if (!rawPassword.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setPhone(userDTO.getPhone());
        user.setAge(userDTO.getAge());
        user.setLocation(userDTO.getLocation());

        User savedUser = userRepository.save(user);
        return convertToUserDTO(savedUser);
    }

    public DealerDTO registerDealer(DealerDTO dealerDTO){
        if (dealerRepository.findByEmail(dealerDTO.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email is already registered.");
        }

        // Validate password BEFORE encoding
        String rawPassword = dealerDTO.getPassword();
        if (!rawPassword.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character");
        }

        Dealer dealer = new Dealer();
        dealer.setUsername(dealerDTO.getUsername());
        dealer.setFirstName(dealerDTO.getFirstName());
        dealer.setLastName(dealerDTO.getLastName());
        dealer.setEmail(dealerDTO.getEmail());
        dealer.setPassword(passwordEncoder.encode(rawPassword));
        dealer.setAge(dealerDTO.getAge());
        dealer.setPhone(dealerDTO.getPhone());
        dealer.setDealershipName(dealerDTO.getDealershipName());
        dealer.setLocation(dealerDTO.getLocation());


        Dealer savedDealer = dealerRepository.save(dealer);
        return convertToDealerDTO(savedDealer);
    }

    public LoginResponse authenticate(LoginRequest request){
        Authenticatable account = findByEmail(request.getEmail());
        if (account == null){
            throw new RuntimeException("Invalid email or password");
        }

        if(!validatePassword(request.getPassword(), account.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(
            account.getEmail(),
            account.getRole(),
            account.getId()
        );

        return new LoginResponse(
                token,
                account.getRole(),
                account.getId(),
                account.getEmail()
        );
    }

    private Authenticatable findByEmail(String email){
        Optional<Authenticatable> user = userRepository.findByEmail(email)
                .map(u -> (Authenticatable) u);
        if (user.isPresent()) {
            return user.get();
        }

        return dealerRepository.findByEmail(email)
                .map(d -> (Authenticatable) d)
                .orElse(null);
    }

    private boolean validatePassword(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    private UserDTO convertToUserDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());
        dto.setPhone(user.getPhone());
        dto.setLocation(user.getLocation());
        return dto;
    }

    private DealerDTO convertToDealerDTO(Dealer dealer){
        DealerDTO dto = new DealerDTO();
        dto.setId(dealer.getId());
        dto.setFirstName(dealer.getFirstName());
        dto.setLastName(dealer.getLastName());
        dto.setEmail(dealer.getEmail());
        dto.setAge(dealer.getAge());
        dto.setPhone(dealer.getPhone());
        dto.setDealershipName(dealer.getDealershipName());
        dto.setLocation(dealer.getLocation());
        return dto;
    }
}
