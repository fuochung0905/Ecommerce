package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.DeliveryDto;
import com.utc2.it.Ecommerce.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    PaymentDto updatePayment(Long paymentId,PaymentDto paymentDto);
    void deletePayment(Long paymentId);
    PaymentDto getPaymentById(Long paymentId);
    List<PaymentDto> getAllPayment();
}
