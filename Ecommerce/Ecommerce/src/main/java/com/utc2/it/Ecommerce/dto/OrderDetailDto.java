package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private  Long orderDetailId;
    private Long orderId;
    private String createDate;
    private String updateDate;
    private String orderStatus;
    private String orderDelivery;
    private String orderPayment;
    private Long productId;
    private String productName;
    private String size;
    private String color;
    private Long userId;
    private String userName;
    private String addressUser;
    private String phoneNumber;
    private String email;
    private int quantity;
    private double price;




}
