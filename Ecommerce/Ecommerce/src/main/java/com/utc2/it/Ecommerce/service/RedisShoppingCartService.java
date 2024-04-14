package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.CartDto;
import com.utc2.it.Ecommerce.entity.ShoppingCartItem;

import java.util.List;

public interface RedisShoppingCartService {
     void addToCart(Long  productId, CartDto cartDto);
     void removeCart(Long productId);
     Long getCartCount();
     List<ShoppingCartItem> getUserCart() throws Exception;
}
