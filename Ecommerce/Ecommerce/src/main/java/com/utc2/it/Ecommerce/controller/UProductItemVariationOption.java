package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.ProductItemVariationOptionDto;
import com.utc2.it.Ecommerce.service.ProductItemVariationOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user/product-item-variation-option")
@RequiredArgsConstructor

public class UProductItemVariationOption {
    private final ProductItemVariationOptionService productItemVariationOptionService;
    @GetMapping("/productItem/{productItemId}")
    public ResponseEntity<?> getProductItemVariationOptionByProductItemId(@PathVariable("productItemId") Long productItemId) {
        List<ProductItemVariationOptionDto>productItemVariationOptionDtos=productItemVariationOptionService.getAllProductItemVariationOptionsByProductItem(productItemId);
        return ResponseEntity.ok(productItemVariationOptionDtos);
    }
}
