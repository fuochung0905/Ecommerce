package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCartDto {
    private Long idColor;
    private Long idSize;
    private Long Id;
    private int quantity;
    private double price;
    private String color;
    private String size;
    private String productName;
    private String image;
    private String addressUser;
    private String username;
    private String orderStatus;
    private String orderDate;
}
