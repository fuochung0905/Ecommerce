package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.ProductColorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/user/product")
@RequiredArgsConstructor
public class UProductController {
    private final ProductService productService;
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        List<ProductDto>productDtos=productService.getAllProduct();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable Long productId){
        ProductDto productDto= productService.getProductById(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @GetMapping("variationOption/{variationOptionId}/{colorId}")
    public ResponseEntity<?>getProductClickColor(@PathVariable Long colorId,@PathVariable Long variationOptionId){
        ProductDto productDtos=productService.getProductByIsColorAndByVariationOption(colorId,variationOptionId);
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }
}