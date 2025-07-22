package com.cyan.shop.mapper;

import com.cyan.shop.dto.OrderItemResponse;
import com.cyan.shop.dto.OrderResponse;
import com.cyan.shop.entity.Order;
import com.cyan.shop.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponse toItemDto(OrderItem item);
    OrderResponse toDto(Order order);

    List<OrderItemResponse> toItemDtoList(List<OrderItem> items);

    @Mapping(target = "userEmail", source = "order.user.email")
    @Mapping(target = "items", source = "items")
    OrderResponse toOrderDto(Order order, List<OrderItem> items);
}
