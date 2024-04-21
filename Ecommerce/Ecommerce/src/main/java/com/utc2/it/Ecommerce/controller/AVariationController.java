package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.DeleteResponse;
import com.utc2.it.Ecommerce.dto.VariationDto;
import com.utc2.it.Ecommerce.service.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/variation")
public class AVariationController {
    private final VariationService variationService;
    @PostMapping("/createNewVariation")
    public ResponseEntity<?>createNewVariation(@RequestBody VariationDto variationDto){
        VariationDto dto=variationService.createNewVariation(variationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @PutMapping("/updateVariation/{variationId}")
    public ResponseEntity<?>updateVariation(@PathVariable Long variationId,VariationDto variationDto){
        VariationDto dto=variationService.updateVariation(variationId,variationDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @DeleteMapping("/deleteVariation/{variationId}")
    public ResponseEntity<DeleteResponse>deleteVariation(@PathVariable Long variationId){
        variationService.deleteVariation(variationId);
        return new ResponseEntity<>(new DeleteResponse("Delete variation successfully"),HttpStatus.OK);
    }
    @GetMapping("/{variationId}")
    public ResponseEntity<?>getVariationById(@PathVariable Long variationId){
        VariationDto variationDto=variationService.getVariationById(variationId);
        return new ResponseEntity<>(variationDto,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<VariationDto>>getAllVariation(){
        List<VariationDto>variationDtos=variationService.getAllVariation();
        return new ResponseEntity<>(variationDtos,HttpStatus.OK);
    }
   @GetMapping("/product/{productId}")
    public ResponseEntity<?>getAllVariationByProduct(@PathVariable Long productId){
        List<VariationDto>variationDtos=variationService.getVariationByProduct(productId);
        return new ResponseEntity<>(variationDtos,HttpStatus.OK);
   }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?>getAllVariationByCategory(@PathVariable Long categoryId){
        List<VariationDto>variationDtos=variationService.getVariationByProduct(categoryId);
        return new ResponseEntity<>(variationDtos,HttpStatus.OK);
    }
}
