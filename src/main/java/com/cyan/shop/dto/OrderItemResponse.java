package com.cyan.shop.dto;

import lombok.Data;

@Data
public class OrderItemResponse {
    private Long id;
    private String productName;
    private double price;
    private int quantity;
}
