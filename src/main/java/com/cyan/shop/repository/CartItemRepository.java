package com.cyan.shop.repository;

import com.cyan.shop.entity.CartItem;
import com.cyan.shop.entity.Product;
import com.cyan.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    void deleteByUserId(Long userId);

    Optional<CartItem> findByUserAndProduct(User user, Product product);
    List<CartItem> findByUserId(Long userId);
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
}
