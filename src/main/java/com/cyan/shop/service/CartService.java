package com.cyan.shop.service;

import com.cyan.shop.dto.CartItemResponse;
import com.cyan.shop.entity.CartItem;
import com.cyan.shop.entity.Product;
import com.cyan.shop.entity.User;
import com.cyan.shop.exception.NotFoundException;
import com.cyan.shop.repository.CartItemRepository;
import com.cyan.shop.repository.ProductRepository;
import com.cyan.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    public void addToCart(String email, Long productId, int quantity) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        Product product = productRepo.findById(productId).orElseThrow();

        CartItem item = CartItem.builder()
                .product(product)
                .quantity(quantity)
                .user(user)
                .build();

        cartRepo.save(item);
    }

    public List<CartItemResponse> getCart(String email) {
        User user = userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
        return cartRepo.findByUser(user).stream().map(cartItem -> {
            CartItemResponse dto = new CartItemResponse();
            dto.setProductName(cartItem.getProduct().getName());
            dto.setQuantity(cartItem.getQuantity());
            dto.setPrice(cartItem.getProduct().getPrice());
            return dto;
        }).toList();
    }

    public void clearCart(User user) {
        cartRepo.deleteByUser(user);
    }
}
