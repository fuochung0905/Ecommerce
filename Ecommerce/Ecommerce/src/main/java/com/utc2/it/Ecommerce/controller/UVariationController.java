package com.utc2.it.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.utc2.it.Ecommerce.dto.VariationOptionDto;
import com.utc2.it.Ecommerce.service.VariationOptionService;

import java.util.List;

@RestController
@RequestMapping("/api/user/variation")
@RequiredArgsConstructor
public class UVariationController {
    private final VariationOptionService variationService;
    @GetMapping("/size/{productId}")
    public ResponseEntity<List<VariationOptionDto>>getVariationOptionWithSizeByProduct(@PathVariable Long productId){
        List<VariationOptionDto>variationOptionDtos=variationService.getAllVariationOptionWithSizeByProduct(productId);
        return new ResponseEntity<>(variationOptionDtos,HttpStatus.OK);
    }
    @GetMapping("/color/{productId}")
    public ResponseEntity<List<VariationOptionDto>>getVariationOptionWithColorByProduct(@PathVariable Long productId){
        List<VariationOptionDto>variationOptionDtos=variationService.getAllVariationOptionWithColorByProduct(productId);
        return new ResponseEntity<>(variationOptionDtos,HttpStatus.OK);
    }
}
