package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.CartDto;
import com.utc2.it.Ecommerce.dto.ProductVariationDto;
import com.utc2.it.Ecommerce.dto.UserCartDto;

import java.util.List;

public interface CartService {
    Integer AddItem(List<ProductVariationDto>productVariationDtos) throws Exception;
    Integer RemoveCartItem(Long productId) throws Exception;
    List<UserCartDto> getUserCart() throws Exception;
    Integer getCartCount();
}
