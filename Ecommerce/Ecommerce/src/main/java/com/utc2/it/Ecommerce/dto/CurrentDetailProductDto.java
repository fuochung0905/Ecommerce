package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CurrentDetailProductDto {
    private Long productId;
    private String image;
    private int quantity;
    private double price;
}
