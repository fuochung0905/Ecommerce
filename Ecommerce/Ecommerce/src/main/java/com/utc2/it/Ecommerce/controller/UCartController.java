package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.ColorSizeDto;
import com.utc2.it.Ecommerce.dto.UserCartDto;
import com.utc2.it.Ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.entity.ShoppingCartItem;
import com.utc2.it.Ecommerce.service.RedisShoppingCartService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/cart")
public class UCartController {
    private final RedisShoppingCartService redisShoppingCartService;
    private final CartService cartService;

    @GetMapping("/count")
    public ResponseEntity<Integer>getCartCount(){
        Integer cartCount= cartService.getCartCount();
        return new ResponseEntity<>(cartCount,HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<String>createCart(@RequestBody ColorSizeDto productVariationDtos) throws Exception {
        redisShoppingCartService.addToCart(productVariationDtos);
        return new ResponseEntity<>("Add successfully", HttpStatus.CREATED);
    }
    @PostMapping("/remove")
    public ResponseEntity<String>removeCart(@RequestBody ColorSizeDto productVariationDtos) throws Exception {
        redisShoppingCartService.removeCart(productVariationDtos);
        return new ResponseEntity<>("Remove successfully", HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<?>getUserCart() throws Exception {
        List<UserCartDto>userCartDtos= cartService.getUserCart();
        return new ResponseEntity<>(userCartDtos,HttpStatus.OK);
    }
}
