package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.VariationDto;
import com.utc2.it.Ecommerce.service.VariationService;
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
@RequestMapping("/api/guest/variation")
@RequiredArgsConstructor
public class UVariationController {
   private final VariationService variationService;
   @GetMapping("/product/{productId}")
    public ResponseEntity<?>getVariationByProduct(@PathVariable Long productId) {
       List<VariationDto>variationDtos=variationService.getVariationUserByProduct(productId);
       if(variationDtos==null || variationDtos.size()==0){
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       return new ResponseEntity<>(variationDtos, HttpStatus.OK);
   }

}
