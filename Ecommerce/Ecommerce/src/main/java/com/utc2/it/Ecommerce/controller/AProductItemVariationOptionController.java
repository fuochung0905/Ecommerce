package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.DeleteResponse;
import com.utc2.it.Ecommerce.dto.ProductItemVariationOptionDto;
import com.utc2.it.Ecommerce.entity.ProductItemVariationOption;
import com.utc2.it.Ecommerce.service.ProductItemVariationOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/product_item_variation_option")
@RequiredArgsConstructor
public class AProductItemVariationOptionController {
    private final ProductItemVariationOptionService productItemVariationOptionService;
    @GetMapping("/productItem/{productItemId}")
    public ResponseEntity<?> getProductItemVariationOption(@PathVariable Long productItemId) {
        List<ProductItemVariationOptionDto>productItemVariationOptionDtos=productItemVariationOptionService.getAllProductItemVariationOptionsByProductItem(productItemId);
        return  ResponseEntity.ok(productItemVariationOptionDtos);
    }
    @DeleteMapping("/{productItemVariationId}")
    public ResponseEntity<?> deleteProductItemVariationOption(@PathVariable Long productItemVariationId) {
        productItemVariationOptionService.deleteProductItemVariationOption(productItemVariationId);
        return new ResponseEntity<>(new DeleteResponse("Delete successfully"), HttpStatus.OK);
    }
    @PostMapping("/{productItemVariationId}")
    public ResponseEntity<?>updateProductItemVariationOption(@PathVariable  Long productItemVariationId,@RequestBody ProductItemVariationOptionDto productItemVariationOptionDto){
        ProductItemVariationOptionDto dto= productItemVariationOptionService.updateProductItemVariationOption(productItemVariationId,productItemVariationOptionDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
}
