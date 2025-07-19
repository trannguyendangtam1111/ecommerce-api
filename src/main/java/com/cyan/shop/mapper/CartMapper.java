package com.cyan.shop.mapper;

import com.cyan.shop.dto.CartItemResponse;
import com.cyan.shop.entity.CartItem;
import com.cyan.shop.entity.Product;
import com.cyan.shop.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Named("getProductId")
    static Long getProductId(Product product) {
        return product.getId();
    }

    @Named("getUserId")
    static Long getUserId(User user) {
        return user.getId();
    }

    @Named("getProductName")
    static String getProductName(Product product) {
        return product.getName();
    }
    @Named("getProductPrice")
    static double getProductPrice(Product product) {
        return product.getPrice();
    }

    @Mapping(target = "productId", source = "product", qualifiedByName = "getProductId")
    @Mapping(target = "productName", source = "product", qualifiedByName = "getProductName")
    @Mapping(target = "price", source = "product", qualifiedByName = "getProductPrice")
    @Mapping(target = "userId", source = "user", qualifiedByName = "getUserId")
    @Mapping(target = "totalPrice", expression = "java(cartItem.getProduct().getPrice() * cartItem.getQuantity())")
    CartItemResponse toDto(CartItem cartItem);

    List<CartItemResponse> toDtoList(List<CartItem> cartItemList);
}
