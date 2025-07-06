package com.cyan.shop.controller;

import com.cyan.shop.dto.OrderResponse;
import com.cyan.shop.entity.Order;
import com.cyan.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order", description = "API for orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Order> placeOrders(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(orderService.placeOrder(email));
    }

    //@PostMapping
    //@Operation(summary = "Create orders")
    //public ResponseEntity<String> createOrder(@AuthenticationPrincipal String email) {
    //    return ResponseEntity.ok("Successfully added orders");
    //}

    @GetMapping("/me")
    @Operation(summary = "View all orders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<OrderResponse>> myOrders(@AuthenticationPrincipal String email) {
        return ResponseEntity.ok(orderService.getMyOrders(email));
    }

    @GetMapping
    @Operation(summary = " Admin view all orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> allOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }


}
