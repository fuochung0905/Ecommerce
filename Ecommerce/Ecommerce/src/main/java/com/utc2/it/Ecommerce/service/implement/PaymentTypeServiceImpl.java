package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.PaymentDto;
import com.utc2.it.Ecommerce.dto.PaymentTypeDto;
import com.utc2.it.Ecommerce.entity.Payment;
import com.utc2.it.Ecommerce.entity.PaymentType;
import com.utc2.it.Ecommerce.repository.PaymentRepository;
import com.utc2.it.Ecommerce.repository.PaymentTypeRepository;
import com.utc2.it.Ecommerce.service.PaymentService;
import com.utc2.it.Ecommerce.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {
    private final PaymentRepository paymentRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    @Override
    public PaymentTypeDto createPaymentType(PaymentTypeDto paymentTypeDto) {
        PaymentType paymentType= new PaymentType();
        paymentType.setValue(paymentTypeDto.getValue());
        paymentType.setImage("");
        Payment payment=paymentRepository.findById(paymentTypeDto.getPaymentId()).orElseThrow();
        paymentType.setPayment(payment);
        PaymentType save= paymentTypeRepository.save(paymentType);
        PaymentTypeDto dto= new PaymentTypeDto();
        dto.setId(save.getId());
        dto.setPaymentId(save.getPayment().getId());
        dto.setImage(save.getImage());
        dto.setValue(save.getValue());
        return dto;
    }

    @Override
    public PaymentTypeDto updatePaymentType(Long paymentTypeId, PaymentTypeDto paymentTypeDto) {
        return null;
    }

    @Override
    public void deletePaymentType(Long paymentTypeId) {

    }

    @Override
    public PaymentTypeDto getPaymentTypeById(Long paymentTypeId) {
        PaymentType paymentType=paymentTypeRepository.findById(paymentTypeId).orElseThrow();
        PaymentTypeDto dto= new PaymentTypeDto();
        dto.setId(paymentType.getId());
        dto.setPaymentId(paymentType.getPayment().getId());
        dto.setImage(paymentType.getImage());
        dto.setValue(paymentType.getValue());
        return dto;
    }

    @Override
    public List<PaymentTypeDto> getAllPaymentType() {
        List<PaymentType>paymentTypes=paymentTypeRepository.findAll();
        List<PaymentTypeDto>paymentTypeDtos=new LinkedList<>();
        for(PaymentType paymentType:paymentTypes){
            PaymentTypeDto dto= new PaymentTypeDto();
            dto.setId(paymentType.getId());
            dto.setPaymentId(paymentType.getPayment().getId());
            dto.setImage(paymentType.getImage());
            dto.setValue(paymentType.getValue());
            paymentTypeDtos.add(dto);
        }
        return paymentTypeDtos;
    }

    @Override
    public List<PaymentTypeDto> getAllPaymentTypeByPaymentId(Long paymentId) {
        Payment payment= paymentRepository.findById(paymentId).orElseThrow();
        List<PaymentType> paymentTypes=paymentTypeRepository.getPaymentTypeByPayment(payment);
        List<PaymentTypeDto>paymentTypeDtos=new LinkedList<>();
        for(PaymentType paymentType:paymentTypes){
            PaymentTypeDto dto= new PaymentTypeDto();
            dto.setId(paymentType.getId());
            dto.setPaymentId(paymentType.getPayment().getId());
            dto.setImage(paymentType.getImage());
            dto.setValue(paymentType.getValue());
            paymentTypeDtos.add(dto);
        }
        return paymentTypeDtos;
    }
}
