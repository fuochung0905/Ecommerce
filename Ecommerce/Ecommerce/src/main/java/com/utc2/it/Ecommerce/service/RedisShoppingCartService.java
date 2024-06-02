package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.ColorSizeDto;

public interface RedisShoppingCartService {
     void addToCart( ColorSizeDto productVariationDtos);
     void removeCart(ColorSizeDto productVariationDtos);

}
