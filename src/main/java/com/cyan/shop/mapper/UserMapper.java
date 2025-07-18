package com.cyan.shop.mapper;

import com.cyan.shop.dto.LoginRequest;
import com.cyan.shop.dto.LoginResponse;
import com.cyan.shop.dto.RegisterRequest;
import com.cyan.shop.dto.UserResponse;
import com.cyan.shop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "role", constant = "CUSTOMER")
    User toEntity(RegisterRequest registerRequest);
    UserResponse toDto(User user);
}