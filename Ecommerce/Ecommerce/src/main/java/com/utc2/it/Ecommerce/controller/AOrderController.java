package com.utc2.it.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.utc2.it.Ecommerce.service.OrderService;

@RestController
@RequestMapping("api/admin/order")
@RequiredArgsConstructor
public class AOrderController {
    private final OrderService orderService;
//  @GetMapping("all")
//    public ResponseEntity<O>
}
