package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.VariationOptionDto;
import com.utc2.it.Ecommerce.service.ProductItemService;
import com.utc2.it.Ecommerce.service.VariationOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/variationOption")

public class UVariationOptionController {
    private final ProductItemService productItemService;
    private final VariationOptionService variationOptionService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getColorProductItem(@PathVariable Long productId) {
        List<VariationOptionDto>variationOptionDtos=variationOptionService.getAllVariationOptionWithByProduct(productId);
        if(variationOptionDtos==null||variationOptionDtos.size()==0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(variationOptionDtos, HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<?>getAllVariationOptions() {
        List<VariationOptionDto>variationOptionDtos=variationOptionService.getAllVariation();
        if(variationOptionDtos==null||variationOptionDtos.size()==0){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(variationOptionDtos, HttpStatus.OK);
    }
}
