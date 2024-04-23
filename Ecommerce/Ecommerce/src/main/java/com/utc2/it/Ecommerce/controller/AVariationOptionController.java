package com.utc2.it.Ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.dto.VariationOptionDto;
import com.utc2.it.Ecommerce.service.VariationOptionService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/variation_option")
@RequiredArgsConstructor
public class AVariationOptionController {
    private final VariationOptionService variationService;
    @PostMapping("/createNewVariationOption")
    public ResponseEntity<?> createVariation(@Valid @RequestBody VariationOptionDto variationDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid request");
        }
        VariationOptionDto dto= variationService.createVariationDto(variationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<VariationOptionDto>>getAllVariation(){
        List<VariationOptionDto>listDto=variationService.getAllVariation();
        return new ResponseEntity<>(listDto,HttpStatus.OK);
    }
    @GetMapping("/{variationOptionId}")
    public ResponseEntity<VariationOptionDto>getVariationById(@PathVariable Long variationOptionId){
        VariationOptionDto dto=variationService.getVariationById(variationOptionId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<?>getAllVariationOptionByProduct(@PathVariable("productId") Long productId) {
        List<VariationOptionDto>variationOptions=variationService.getAllVariationByProduct(productId);
        return new ResponseEntity<>(variationOptions, HttpStatus.OK);
    }
    @GetMapping("/size/product/{productId}")
    public ResponseEntity<?>getAllVariationOptionWithSizeByProduct(@PathVariable Long productId) {
        List<VariationOptionDto>variationOptionDtos=variationService.getAllVariationOptionWithSizeByProduct(productId);
        return new ResponseEntity<>(variationOptionDtos, HttpStatus.OK);
    }
    @GetMapping("/color/product/{productId}")
    public ResponseEntity<?>getAllVariationOptionWithColorByProduct(@PathVariable Long productId) {
        List<VariationOptionDto>variationOptionDtos=variationService.getAllVariationOptionWitColorByProduct(productId);
        return new ResponseEntity<>(variationOptionDtos, HttpStatus.OK);
    }
    @GetMapping("/productItem/{productItemId}")
    public ResponseEntity<?>getAllVariationOptionWithSizeByProductItem(@PathVariable Long productItemId){
        List<VariationOptionDto>variationOptionDtos=variationService.getAllVariationOptionWithSizeByProductItem(productItemId);
        return new ResponseEntity<>(variationOptionDtos, HttpStatus.OK);
    }


}
