package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductItemVariationOptionDto {
    private Long productItemId;
    private Long idColor;
    private Long variationOptionId;
    private String value;
    private int quantity;
    private Long productId;
    private Long id;
}
