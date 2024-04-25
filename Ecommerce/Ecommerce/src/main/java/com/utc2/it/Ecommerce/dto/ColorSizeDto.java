package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ColorSizeDto {
    private Long idColor;
    private Long variationOptionId;
    private int quantity;
}
