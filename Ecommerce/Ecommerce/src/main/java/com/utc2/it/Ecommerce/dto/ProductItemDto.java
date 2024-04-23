package com.utc2.it.Ecommerce.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductItemDto {
    private Long Id;
    private int qyt_stock;
    private String image;
    private double price;
    private Long productId;
    private Long idColor;
}
