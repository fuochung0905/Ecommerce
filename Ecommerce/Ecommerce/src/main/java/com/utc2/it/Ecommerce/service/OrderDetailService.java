package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.OrderDetailDto;

public interface OrderDetailService {
    OrderDetailDto getInformationOrderByOrderDetailId(Long orderDetailId);
}
