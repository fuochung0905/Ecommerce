package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.PaymentDto;
import com.utc2.it.Ecommerce.entity.Payment;
import com.utc2.it.Ecommerce.service.PaymentService;
import com.utc2.it.Ecommerce.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/payment")
public class UPaymentController {
    private final PaymentService paymentService;
    @GetMapping("/")
    public ResponseEntity<?>getAllPayment(){
        List<PaymentDto>payments=paymentService.getAllPayment();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }
}
