package com.cyan.shop.service;

import com.cyan.shop.dto.AddToCartRequest;
import com.cyan.shop.dto.CartItemResponse;
import com.cyan.shop.entity.CartItem;
import com.cyan.shop.entity.Product;
import com.cyan.shop.entity.User;
import com.cyan.shop.exception.NotFoundException;
import com.cyan.shop.mapper.CartMapper;
import com.cyan.shop.repository.CartItemRepository;
import com.cyan.shop.repository.ProductRepository;
import com.cyan.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    private final CartMapper cartMapper;

    public CartItemResponse addToCart(AddToCartRequest request, Long userId) {
        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Optional<CartItem> existingItemOpt = cartRepo
                .findByUserAndProduct(user, product);
        CartItem cartItem;
        if (existingItemOpt.isPresent()) {
            cartItem = existingItemOpt.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
        }
        cartRepo.save(cartItem);
        return cartMapper.toDto(cartItem);
    }

    public List<CartItemResponse> getCart(Long userId) {
        List<CartItem> cartItems = cartRepo.findByUserId(userId);
        return cartMapper.toDtoList(cartItems);
    }

    public List<CartItemResponse> getUserCart(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        List<CartItem> cartItems = cartRepo.findByUser(user);

        return cartMapper.toDtoList(cartItems);
    }

    public void removeProductFromCart(Long userId, Long productId) {
        CartItem cartItem = cartRepo.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new NotFoundException("Product not found in cart"));
        cartRepo.delete(cartItem);
    }

    @Transactional
    public void clearCart(Long userId) {
        cartRepo.deleteByUserId(userId);
    }
}
