package com.cyan.shop.dto;

import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private double total;
    private String status;
    private String userEmail;
}
