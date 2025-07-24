package com.cyan.shop.mapper;

import com.cyan.shop.dto.LoginRequest;
import com.cyan.shop.dto.LoginResponse;
import com.cyan.shop.dto.RegisterRequest;
import com.cyan.shop.dto.UserResponse;
import com.cyan.shop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", imports = Set.class)
public interface UserMapper {
    @Mapping(target = "roles", expression = "java(Set.of(com.cyan.shop.enums.Role.ADMIN))")
    User toEntity(RegisterRequest registerRequest);
    UserResponse toDto(User user);
}