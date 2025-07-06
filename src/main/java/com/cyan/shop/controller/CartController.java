package com.cyan.shop.controller;

import com.cyan.shop.dto.CartItemResponse;
import com.cyan.shop.entity.CartItem;
import com.cyan.shop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart", description = "API for cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    @Operation(summary = "Add products to cart")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> add(@RequestParam Long productId,
                                @RequestParam int quantity,
                                @AuthenticationPrincipal String email){
        cartService.addToCart(email, productId, quantity);
        return ResponseEntity.ok("Successfully added product");
    }

    @GetMapping
    @Operation(summary = "View my cart")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CartItemResponse>> view(@AuthenticationPrincipal String email){
        return ResponseEntity.ok(cartService.getCart(email));
    }
}
