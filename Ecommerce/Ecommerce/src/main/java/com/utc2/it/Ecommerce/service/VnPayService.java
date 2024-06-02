package com.utc2.it.Ecommerce.service;

import jakarta.servlet.http.HttpServletRequest;

public interface VnPayService {
    int orderReturn(HttpServletRequest request);
    String  createOrder(int cartId,int deliveryId,int total, String orderInfor, String urlReturn);
}
