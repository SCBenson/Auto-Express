package com.ct5221.auto_express.service;

import com.ct5221.auto_express.domain.DealerRepository;
import com.ct5221.auto_express.domain.UserRepository;
import com.ct5221.auto_express.dto.LoginRequest;
import com.ct5221.auto_express.dto.LoginResponse;
import com.ct5221.auto_express.security.Authenticatable;
import com.ct5221.auto_express.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private JwtUtil jwtUtil;

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
        return rawPassword.equals(encodedPassword);
    }
}
