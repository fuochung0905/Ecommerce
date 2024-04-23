package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.ProductVariationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.dto.CartDto;
import com.utc2.it.Ecommerce.entity.ShoppingCartItem;
import com.utc2.it.Ecommerce.service.RedisShoppingCartService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/cart")
public class UCartController {
    private final RedisShoppingCartService redisShoppingCartService;

    @GetMapping("/count")
    public ResponseEntity<Long>getCartCount(){
        Long cartCount= redisShoppingCartService.getCartCount();
        return new ResponseEntity<>(cartCount,HttpStatus.OK);
    }
    @PutMapping("/add")
    public ResponseEntity<String>createCart(@RequestBody List<ProductVariationDto>productVariationDtos) throws Exception {
        redisShoppingCartService.addToCart(productVariationDtos);
        return new ResponseEntity<>("Add successfully", HttpStatus.CREATED);
    }
        @PutMapping("/remove/{productId}")
    public ResponseEntity<String>removeCart(@PathVariable Long productId) throws Exception {
       redisShoppingCartService.removeCart(productId);
        return new ResponseEntity<>("Delete successfully",HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<ShoppingCartItem>>getUserCart() throws Exception {
        List<ShoppingCartItem>userCartDtos= redisShoppingCartService.getUserCart();
        return new ResponseEntity<>(userCartDtos,HttpStatus.OK);
    }
}
