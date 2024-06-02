package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.CartDetailDto;
import com.utc2.it.Ecommerce.entity.CartDetail;
import com.utc2.it.Ecommerce.repository.CartDetailRepository;
import com.utc2.it.Ecommerce.service.CartDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {
    private final CartDetailRepository cartDetailRepository;
    @Override
    public CartDetailDto getCartDetailById(Long cartDetailById) {
        CartDetail cartDetail=cartDetailRepository.findById(cartDetailById).orElseThrow();
        CartDetailDto cartDetailDto= new CartDetailDto();
        cartDetailDto.setId(cartDetail.getId());
        cartDetailDto.setQuantity(cartDetail.getQuantity());
        cartDetailDto.setPrice(cartDetail.getPrice());
        return cartDetailDto;
    }
}
