package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.CartDetailDto;

import java.util.List;

public interface CartDetailService {
 CartDetailDto getCartDetailById(Long cartDetailById);
 List<CartDetailDto> getAllCartDetail();
}
