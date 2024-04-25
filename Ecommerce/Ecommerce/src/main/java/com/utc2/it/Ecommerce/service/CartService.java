package com.utc2.it.Ecommerce.service;
import com.utc2.it.Ecommerce.dto.UserCartDto;
import java.util.List;

public interface CartService {

    List<UserCartDto> getUserCart() throws Exception;
    Integer getCartCount();
}
