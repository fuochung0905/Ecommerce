package com.utc2.it.Ecommerce.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.dto.VariationDto;
import com.utc2.it.Ecommerce.service.VariationService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/variation")
@RequiredArgsConstructor
public class VariationController {
    private final VariationService variationService;
    @PostMapping("/create")
    public ResponseEntity<?> createVariation(@Valid @RequestBody VariationDto variationDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Invalid request");
        }
        VariationDto dto= variationService.createVariationDto(variationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<VariationDto>>getAllVariation(){
        List<VariationDto>listDto=variationService.getAllVariation();
        return new ResponseEntity<>(listDto,HttpStatus.OK);
    }
    @GetMapping("/{variationId}")
    public ResponseEntity<VariationDto>getVariationById(@PathVariable Long variationId){
        VariationDto dto=variationService.getVariationById(variationId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/category/{productId}")
    public ResponseEntity<List<VariationDto>>getAllVariationByCategory(@PathVariable Long productId){
        List<VariationDto>listVariation=variationService.getAllVariationByCategory(productId);
        return new ResponseEntity<>(listVariation,HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<VariationDto>>getAllVariationByProduct(@PathVariable Long productId){
        List<VariationDto>listVariation=variationService.getAllVariationByProduct(productId);
        return new ResponseEntity<>(listVariation,HttpStatus.OK);
    }
}
