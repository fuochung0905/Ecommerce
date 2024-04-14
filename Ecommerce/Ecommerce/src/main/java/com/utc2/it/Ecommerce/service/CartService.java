package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.CartDto;
import com.utc2.it.Ecommerce.dto.UserCartDto;

import java.util.List;

public interface CartService {
    Integer AddItem(Long productId, CartDto cartDto) throws Exception;
    Integer RemoveCartItem(Long productId) throws Exception;
    List<UserCartDto> getUserCart() throws Exception;
    Integer getCartCount();
}
