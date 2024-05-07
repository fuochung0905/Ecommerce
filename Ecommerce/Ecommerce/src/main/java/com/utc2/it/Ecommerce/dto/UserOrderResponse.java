package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserOrderResponse {
    private Long orderId;
    private String productName;
    private String image;
    private String date;
    private double totalPrice;


}
