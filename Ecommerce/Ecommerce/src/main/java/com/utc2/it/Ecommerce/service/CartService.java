package com.utc2.it.Ecommerce.service;
import com.utc2.it.Ecommerce.dto.ColorSizeDto;
import com.utc2.it.Ecommerce.dto.ExceptionCartDto;
import com.utc2.it.Ecommerce.dto.UserCartDto;
import java.util.List;

public interface CartService {
    ExceptionCartDto addToCart(ColorSizeDto productVariationDtos);
    void removeCart(ColorSizeDto productVariationDtos);
    List<UserCartDto> getUserCart() throws Exception;
    Integer getCartCount();
    void deleteCartById(Long cartId);
}
