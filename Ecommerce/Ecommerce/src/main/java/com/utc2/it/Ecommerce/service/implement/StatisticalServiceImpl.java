package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.service.StatisticalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class StatisticalServiceImpl implements StatisticalService {
    @Override
    public Double getTotalAmountByProduct(Long productId) {
        return 0.0;
    }

    @Override
    public Double getTotalAmountMonth() {
        return 0.0;
    }

    @Override
    public Double getTotalAmountYear() {
        return 0.0;
    }

    @Override
    public Double getTotalAmountWeek() {
        return 0.0;
    }

    @Override
    public Integer quantityOrder() {
        return 0;
    }
}
