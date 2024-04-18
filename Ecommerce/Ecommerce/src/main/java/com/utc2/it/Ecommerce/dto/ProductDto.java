package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long Id;
    private Long categoryId;
    private String productName;
    private  String description;
    private String image;




}
