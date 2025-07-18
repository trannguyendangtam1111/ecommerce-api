package com.cyan.shop.service;

import com.cyan.shop.dto.LoginRequest;
import com.cyan.shop.dto.LoginResponse;
import com.cyan.shop.dto.RegisterRequest;
import com.cyan.shop.dto.UserResponse;
import com.cyan.shop.entity.User;
import com.cyan.shop.exception.EmailAlreadyExistsException;
import com.cyan.shop.exception.NameAlreadyExistsException;
import com.cyan.shop.exception.NotFoundException;
import com.cyan.shop.mapper.UserMapper;
import com.cyan.shop.repository.UserRepository;
import com.cyan.shop.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    public UserResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        if(userRepository.existsByName(request.getName())) {
            throw new NameAlreadyExistsException("Name already exists");
        }
        User user = userMapper.toEntity(request);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new LoginResponse(token);
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User does not exist"));
        userRepository.delete(user);
    }
}
