package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.PaymentTypeDto;
import com.utc2.it.Ecommerce.repository.PaymentRepository;
import com.utc2.it.Ecommerce.service.PaymentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin/paymentType")
public class APaymentTypeController {
    private final PaymentTypeService paymentTypeService;
    @PostMapping("/")
    public ResponseEntity<?>createPaymentType(@RequestBody PaymentTypeDto paymentTypeDto){
        PaymentTypeDto dto= paymentTypeService.createPaymentType(paymentTypeDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<?>getAllPaymentType(){
        List<PaymentTypeDto>paymentTypeDtos= paymentTypeService.getAllPaymentType();
        return new ResponseEntity<>(paymentTypeDtos,HttpStatus.OK);
    }
    @GetMapping("/{paymentTypeId}")
    public ResponseEntity<?>getPaymentTypeById(@PathVariable Long paymentTypeId){
        PaymentTypeDto paymentTypeDto=paymentTypeService.getPaymentTypeById(paymentTypeId);
        return new ResponseEntity<>(paymentTypeDto,HttpStatus.OK);
    }
    @GetMapping("/payment/{paymentTypeId}")
    public ResponseEntity<?>getAllPaymentTypeByPaymentId(@PathVariable Long paymentTypeId){
        List<PaymentTypeDto>paymentTypeDtos=paymentTypeService.getAllPaymentTypeByPaymentId(paymentTypeId);
        return new ResponseEntity<>(paymentTypeDtos,HttpStatus.OK);
    }

}
