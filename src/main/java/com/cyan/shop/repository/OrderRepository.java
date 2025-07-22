package com.cyan.shop.repository;

import com.cyan.shop.entity.Order;
import com.cyan.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    void deleteByUser(User user);
    List<Order> findByUser(User user);
}
