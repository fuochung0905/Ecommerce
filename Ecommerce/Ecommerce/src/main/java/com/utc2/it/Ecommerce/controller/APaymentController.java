package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.PaymentDto;
import com.utc2.it.Ecommerce.dto.PaymentTypeDto;
import com.utc2.it.Ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/payment")
@RequiredArgsConstructor
public class APaymentController {
    private final PaymentService paymentService;
    @PostMapping("")
    public ResponseEntity<?>createPayment(@RequestBody PaymentDto paymentDto){
        PaymentDto dto=paymentService.createPayment(paymentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<?>getAllPayment(){
        List<PaymentDto>paymentDtos=paymentService.getAllPayment();
        return new ResponseEntity<>(paymentDtos,HttpStatus.OK);
    }
    @GetMapping("/{paymentId}")
    public ResponseEntity<?>getPaymentById(@PathVariable Long paymentId){
        PaymentDto paymentDto= paymentService.getPaymentById(paymentId);
        return new ResponseEntity<>(paymentDto,HttpStatus.OK);
    }
}
