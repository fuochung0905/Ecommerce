package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariationOptionDto {
    private Long Id;
    private String value;
    private Long variationId;
    private String variationName;
    private String categoryName;

}
