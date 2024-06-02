package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.CartDetailDto;
import com.utc2.it.Ecommerce.service.CartDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/cartDetail")
public class UCartDetailController {
    private final CartDetailService cartDetailService;
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDetailDto>getCartDetailById(@PathVariable Long cartId){
        CartDetailDto cartDetailDto=cartDetailService.getCartDetailById(cartId);
        return new ResponseEntity<>(cartDetailDto, HttpStatus.OK);
    }
}
