package com.utc2.it.Ecommerce.controller;


import com.utc2.it.Ecommerce.dto.PaymentTypeDto;
import com.utc2.it.Ecommerce.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user/paymentType")
@RequiredArgsConstructor
public class UPaymentTypeController {
    private final PaymentTypeService paymentTypeService;
    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<?> getAllPaymentTypeByPaymentId(@PathVariable Long paymentId){
        List<PaymentTypeDto> paymentTypeDtos=paymentTypeService.getAllPaymentTypeByPaymentId(paymentId);
        return new ResponseEntity<>(paymentTypeDtos, HttpStatus.OK);
    }
}
