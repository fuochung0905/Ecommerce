package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariationDto {
    private Long Id;
    private String name;
    private String value;
    private Long categoryId;
}
