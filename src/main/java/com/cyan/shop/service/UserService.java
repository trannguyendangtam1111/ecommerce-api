package com.cyan.shop.service;

import com.cyan.shop.dto.LoginRequest;
import com.cyan.shop.dto.LoginResponse;
import com.cyan.shop.dto.RegisterRequest;
import com.cyan.shop.dto.UserResponse;
import com.cyan.shop.entity.User;
import com.cyan.shop.exception.NotFoundException;
import com.cyan.shop.repository.UserRepository;
import com.cyan.shop.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword()) //todo...BCrypt
                .role("CUSTOMER")
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

//    public UserResponse register(RegisterRequest request) {
//        User user = User.builder()
//                .email(request.getEmail())
//                .password(request.getPassword())
//                .name(request.getName())
//                .role("CUSTOMER")
//                .build();
//
//        userRepository.save(user);
//
//        UserResponse dto = new UserResponse();
//        dto.setId(user.getId());
//        dto.setName(user.getName());
//        dto.setEmail(user.getEmail());
//
//        return dto;
//    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new LoginResponse(token);
    }

//    public String deleteUser(String token) {
//        String email = jwtUtil.extractEmail(token);
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new NotFoundException("User not found"));
//        userRepository.delete(user);
//        return "User deleted successfully";
//    }
//
//    public String deleteUserByEmail(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new NotFoundException("User not found"));
//        userRepository.deleteByEmail(email);
//        userRepository.delete(user);
//        return "User deleted successfully";
//    }
}
