package com.utc2.it.Ecommerce.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.utc2.it.Ecommerce.dto.VariationDto;
import com.utc2.it.Ecommerce.service.VariationService;

import java.util.List;

@RestController
@RequestMapping("/api/user/variation")
@RequiredArgsConstructor

public class UVariationController {
    private final VariationService variationService;
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<VariationDto>> getAllVariationByProduct(@PathVariable Long productId){
        List<VariationDto>listVariation=variationService.getAllVariationByProduct(productId);
        return new ResponseEntity<>(listVariation, HttpStatus.OK);
    }
}
