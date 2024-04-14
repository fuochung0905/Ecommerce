package com.utc2.it.Ecommerce.controller.user;

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
public class CartController {

    private final RedisShoppingCartService redisShoppingCartService;

    @GetMapping("/count")
    public ResponseEntity<Long>getCartCount(){
        Long cartCount= redisShoppingCartService.getCartCount();
        return new ResponseEntity<>(cartCount,HttpStatus.OK);
    }
    @PutMapping("/add/{productId}")
    public ResponseEntity<String>createCart(@RequestBody CartDto cartDto, @PathVariable Long productId) throws Exception {
        redisShoppingCartService.addToCart(productId,cartDto);
        return new ResponseEntity<>("Add successfully", HttpStatus.CREATED);
    }
        @PutMapping("/remove/{productId}")
    public ResponseEntity<String>removeCart(@PathVariable Long productId) throws Exception {
       redisShoppingCartService.removeCart(productId);
        return new ResponseEntity<>("Delete successfully",HttpStatus.CREATED);
    }


//    @PutMapping("/add/{productId}")
//    public ResponseEntity<Integer>addCart(@RequestBody CartDto cartDto, @PathVariable Long productId) throws Exception {
//        Integer cartCount=cartService.AddItem(productId,cartDto);
//        return new ResponseEntity<>(cartCount, HttpStatus.CREATED);
//    }
//    @PutMapping("/remove/{productId}")
//    public ResponseEntity<Integer>removeCart(@PathVariable Long productId) throws Exception {
//        Integer cartCount=cartService.RemoveCartItem(productId);
//        return new ResponseEntity<>(cartCount,HttpStatus.CREATED);
//    }
    @GetMapping("/")
    public ResponseEntity<List<ShoppingCartItem>>getUserCart() throws Exception {
        List<ShoppingCartItem>userCartDtos= redisShoppingCartService.getUserCart();
        return new ResponseEntity<>(userCartDtos,HttpStatus.OK);
    }
}
