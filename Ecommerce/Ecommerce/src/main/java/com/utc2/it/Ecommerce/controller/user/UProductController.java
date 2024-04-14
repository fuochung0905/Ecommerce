package com.utc2.it.Ecommerce.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
