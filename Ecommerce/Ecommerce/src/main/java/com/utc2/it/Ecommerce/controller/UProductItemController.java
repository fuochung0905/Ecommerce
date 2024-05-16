package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.dto.ProductItemDto;
import com.utc2.it.Ecommerce.service.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/guest/productItem")
@RequiredArgsConstructor
public class UProductItemController {
    private final ProductItemService productItemService;
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductItem(@PathVariable Long productId) {
        List<ProductItemDto>productItemDtos=productItemService.getAllProductItemByProduct(productId);
        if(productItemDtos.size()==0||productItemDtos==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productItemDtos);
    }
    @GetMapping("/{productItemId}")
    public ResponseEntity<?> getProductItemById(@PathVariable Long productItemId) {
        ProductDto productItemDto=productItemService.getProductItemById(productItemId);
        if(productItemDto==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productItemDto);
    }
}
