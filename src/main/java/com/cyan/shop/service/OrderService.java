package com.cyan.shop.service;

import com.cyan.shop.dto.OrderResponse;
import com.cyan.shop.entity.CartItem;
import com.cyan.shop.entity.Order;
import com.cyan.shop.entity.OrderItem;
import com.cyan.shop.entity.User;
import com.cyan.shop.exception.NotFoundException;
import com.cyan.shop.mapper.OrderMapper;
import com.cyan.shop.repository.CartItemRepository;
import com.cyan.shop.repository.OrderItemRepository;
import com.cyan.shop.repository.OrderRepository;
import com.cyan.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartItemRepository cartRepo;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final UserRepository userRepo;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse placeOrder(String email){
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        List<CartItem> cartItems = cartRepo.findByUser(user);

        double total = 0;
        Order order = Order.builder()
                .status("NEW")
                .user(user)
                .build();
        orderRepo.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem item : cartItems) {
            double subtotal = item.getProduct().getPrice() * item.getQuantity();
            total += subtotal;

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(item.getProduct())
                    .quantity(item.getQuantity())
                    .price(item.getProduct().getPrice())
                    .build();
            orderItemRepo.save(orderItem);
            orderItems.add(orderItem);
        }

        order.setTotal(total);
        order.setItems(orderItems);
        orderRepo.save(order);
        cartRepo.deleteByUserId(user.getId());

        return orderMapper.toOrderDto(order, orderItems);
    }

    public List<OrderResponse> getMyOrders(String email){
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return orderRepo.findAll().stream()
                .filter(order -> order.getUser().equals(user))
                .map(order -> {
                    List<OrderItem> items = orderItemRepo.findByOrder(order);
                    return orderMapper.toOrderDto(order, items);
                }).toList();
    }

    public List<OrderResponse> getAllOrders(){
        return orderRepo.findAll().stream()
                .map(order -> {
                    List<OrderItem> items = orderItemRepo.findByOrder(order);
                    return orderMapper.toOrderDto(order, items);
                }).toList();
    }
}
