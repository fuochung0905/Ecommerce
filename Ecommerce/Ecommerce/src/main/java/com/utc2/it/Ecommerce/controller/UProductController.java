package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.Base.BaseDto;
import com.utc2.it.Ecommerce.dto.ProductColorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/guest/product")
@RequiredArgsConstructor
public class UProductController {
    private final ProductService productService;
    @GetMapping("/search")
    public ResponseEntity<?>getAllProductBySearchLikeName(@RequestParam String productName){
        BaseDto<List<ProductDto>> productDtos=productService.getAllProductSearchLikeName(productName);
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllProduct(){
        BaseDto<List<ProductDto>> productDtos=productService.getAllProduct();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getAllProductByCategory(@PathVariable Long categoryId){
        BaseDto<List<ProductDto>> productDtos=productService.getAllProductByCategory(categoryId);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?>getProductById(@PathVariable Long productId){
        BaseDto<ProductDto> productDto= productService.getProductById(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @GetMapping("/variationOption/{variationOptionId}/{colorId}")
    public ResponseEntity<?>getProductClickColor(@PathVariable Long colorId,@PathVariable Long variationOptionId){
        BaseDto<ProductDto> productDtos=productService.getProductByIsColorAndByVariationOption(colorId,variationOptionId);
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }
}
