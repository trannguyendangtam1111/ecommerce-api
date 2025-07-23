package com.cyan.shop.controller;

import com.cyan.shop.dto.AddToCartRequest;
import com.cyan.shop.dto.CartItemResponse;
import com.cyan.shop.entity.CartItem;
import com.cyan.shop.security.CustomUserDetails;
import com.cyan.shop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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
    public ResponseEntity<String> add(@RequestBody AddToCartRequest request,
                                      @AuthenticationPrincipal Jwt jwt) {

        cartService.addToCart(request, jwt.getClaim("id"));
        return ResponseEntity.ok("Successfully added product");
    }

    @GetMapping
    @Operation(summary = "View my cart")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<CartItemResponse>> view(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok(cartService.getCart(jwt.getClaim("id")));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItemResponse>> viewUserCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

    @DeleteMapping("/product/{productId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> removeProduct(@PathVariable Long productId,
                                                @AuthenticationPrincipal Jwt jwt) {
        cartService.removeProductFromCart(jwt.getClaim("id"), productId);
        return ResponseEntity.ok("Successfully removed product from cart");
    }

    @DeleteMapping("/clear")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> clearCart(@AuthenticationPrincipal Jwt jwt) {
        cartService.clearCart(jwt.getClaim("id"));
        return ResponseEntity.ok("Cart has been cleared");
    }
}
