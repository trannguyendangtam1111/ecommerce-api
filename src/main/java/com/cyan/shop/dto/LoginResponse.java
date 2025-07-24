package com.cyan.shop.dto;

import com.cyan.shop.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Set<Role> role;
}
