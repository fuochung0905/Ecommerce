package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.PaymentDto;
import com.utc2.it.Ecommerce.dto.PaymentTypeDto;
import java.util.List;

public interface PaymentTypeService {
    PaymentTypeDto createPaymentType(PaymentTypeDto paymentTypeDto);
    PaymentTypeDto updatePaymentType(Long paymentTypeId,PaymentTypeDto paymentTypeDto);
    void deletePaymentType(Long paymentTypeId);
    PaymentTypeDto getPaymentTypeById(Long paymentTypeId);
    List<PaymentTypeDto> getAllPaymentType();
    List<PaymentTypeDto> getAllPaymentTypeByPaymentId(Long paymentId);
}
