package com.cyan.shop.dto;

import lombok.Data;

@Data
public class CartItemResponse {
    private String productName;
    private double price;
    private int quantity;
}
