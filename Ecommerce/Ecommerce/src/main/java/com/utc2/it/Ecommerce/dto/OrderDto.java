package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long Id;
    private int quantity;
    private String size;
    private String color;
    private double price;
    private String addressUser;
    private Long productId;
}
