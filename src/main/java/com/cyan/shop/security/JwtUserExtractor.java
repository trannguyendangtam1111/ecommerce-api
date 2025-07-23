package com.cyan.shop.security;

import com.cyan.shop.security.CustomUserDetails;
import com.cyan.shop.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUserExtractor {
    private final JwtUtil jwtUtil;

    public CustomUserDetails extractUser(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtUtil.getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return CustomUserDetails.fromClaims(claims);
    }
}
