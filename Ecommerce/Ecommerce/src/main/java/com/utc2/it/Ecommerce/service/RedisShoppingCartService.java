package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.ColorSizeDto;
import com.utc2.it.Ecommerce.entity.ShoppingCartItem;

import java.util.List;

public interface RedisShoppingCartService {
     void addToCart( ColorSizeDto productVariationDtos);
     void removeCart(ColorSizeDto productVariationDtos);
     Long getCartCount();
     List<ShoppingCartItem> getUserCart() throws Exception;
}
