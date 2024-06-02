package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.PaymentDto;
import com.utc2.it.Ecommerce.entity.Payment;
import com.utc2.it.Ecommerce.repository.PaymentRepository;
import com.utc2.it.Ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setName(paymentDto.getName());
        Payment save = paymentRepository.save(payment);
        PaymentDto paymentDto1 = new PaymentDto();
        paymentDto1.setId(save.getId());
        paymentDto1.setName(save.getName());
        return paymentDto1;
    }

    @Override
    public PaymentDto updatePayment(Long paymentId, PaymentDto paymentDto) {
        Payment payment= paymentRepository.findById(paymentId).orElseThrow();
        payment.setName(paymentDto.getName());
        Payment save=paymentRepository.save(payment);
        PaymentDto paymentDto1 = new PaymentDto();
        paymentDto1.setId(save.getId());
        paymentDto1.setName(save.getName());
        return paymentDto1;
    }

    @Override
    public void deletePayment(Long paymentId) {
        Payment payment= paymentRepository.findById(paymentId).orElseThrow();
        paymentRepository.delete(payment);
    }

    @Override
    public PaymentDto getPaymentById(Long paymentId) {
        Payment payment= paymentRepository.findById(paymentId).orElseThrow();
        PaymentDto paymentDto1 = new PaymentDto();
        paymentDto1.setId(payment.getId());
        paymentDto1.setName(payment.getName());
        return paymentDto1;
    }

    @Override
    public List<PaymentDto> getAllPayment() {
        List<Payment>payments=paymentRepository.findAll();
        List<PaymentDto>paymentDtos=new LinkedList<>();
        for(Payment payment:payments){
            PaymentDto paymentDto= new PaymentDto();
            paymentDto.setName(payment.getName());
            paymentDto.setId(payment.getId());
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }
}

