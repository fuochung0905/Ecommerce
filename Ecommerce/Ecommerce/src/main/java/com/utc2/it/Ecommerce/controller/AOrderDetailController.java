package com.utc2.it.Ecommerce.controller;


import com.utc2.it.Ecommerce.dto.OrderDetailDto;
import com.utc2.it.Ecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/order-detail")
@RequiredArgsConstructor
public class AOrderDetailController {
    private final OrderDetailService orderDetailService;
    @GetMapping("/{orderDetailId}")
    public ResponseEntity<?>getInformationOrder(@PathVariable Long orderDetailId){
        OrderDetailDto orderDetailDto=orderDetailService.getInformationOrderByOrderDetailId(orderDetailId);
        return new ResponseEntity<>(orderDetailDto, HttpStatus.OK);
    }
}
