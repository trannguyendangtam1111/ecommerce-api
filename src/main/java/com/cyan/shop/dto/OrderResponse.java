package com.cyan.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private double total;
    private String status;
    private String userEmail;

    private List<OrderItemResponse> items;
}
