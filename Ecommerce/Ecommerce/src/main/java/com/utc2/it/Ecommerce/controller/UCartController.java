package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.ColorSizeDto;
import com.utc2.it.Ecommerce.dto.DeleteResponse;
import com.utc2.it.Ecommerce.dto.ExceptionCartDto;
import com.utc2.it.Ecommerce.dto.UserCartDto;
import com.utc2.it.Ecommerce.service.CartService;
import io.lettuce.core.dynamic.annotation.Param;
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

    private final CartService cartService;

    @GetMapping("/count")
    public ResponseEntity<Integer>getCartCount(){
        Integer cartCount= cartService.getCartCount();
        return new ResponseEntity<>(cartCount,HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<ExceptionCartDto>createCart(@RequestBody ColorSizeDto productVariationDtos) throws Exception {
      ExceptionCartDto exceptionCartDto= cartService.addToCart(productVariationDtos);
        return new ResponseEntity<>(exceptionCartDto, HttpStatus.CREATED);
    }
    @PostMapping("/remove")
    public ResponseEntity<String>removeCart(@RequestBody ColorSizeDto productVariationDtos) throws Exception {
        cartService.removeCart(productVariationDtos);
        return new ResponseEntity<>("Remove successfully", HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<?>getUserCart() throws Exception {
        List<UserCartDto>userCartDtos= cartService.getUserCart();
        if (userCartDtos==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(userCartDtos,HttpStatus.OK);
    }
    @DeleteMapping("/{cartId}")
    public ResponseEntity<DeleteResponse>deleteCartById(@PathVariable Long cartId){
        cartService.deleteCartById(cartId);
        return new ResponseEntity<>(new DeleteResponse("Xóa thành công"),HttpStatus.OK);
    }
}
