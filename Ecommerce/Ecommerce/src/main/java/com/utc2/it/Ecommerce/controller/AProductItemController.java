package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.Base.BaseDto;
import com.utc2.it.Ecommerce.dto.*;
import com.utc2.it.Ecommerce.service.ProductItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
    @RequestMapping("api/admin/productItem")
public class AProductItemController {
    private final ProductItemService productItemService;

    @PostMapping("/createNewProductItem")
    public ResponseEntity<?>createNewProductItem( ProductItemDto dto, @RequestParam("file") MultipartFile file)throws IOException {

        BaseDto<ProductItemDto> productDtoBaseDto = productItemService.addProductItem(dto,file);
        return new ResponseEntity<>(productDtoBaseDto, HttpStatus.CREATED);
    }
    @PutMapping("/v/{productItemId}")
    public ResponseEntity<?>updateProductItem(@PathVariable Long productItemId,@RequestBody ProductItemDto dto) throws IOException {
        ProductItemDto productItemDto=productItemService.updateProductItem(productItemId,dto);
        return new ResponseEntity<>(productItemDto,HttpStatus.OK);
    }
    @DeleteMapping("/deleteProductItem/{productItemId}")
    public ResponseEntity<DeleteResponse>deleteProductItem(@PathVariable Long productItemId){
        productItemService.deleteProductItemById(productItemId);
        return new ResponseEntity<>(new DeleteResponse("Delete productItem successfully"),HttpStatus.OK);
    }
    @GetMapping("/{productItemId}")
    public ResponseEntity<?>getProductItemById(@PathVariable Long productItemId){
        ProductDto productItemDto= productItemService.getProductItemById(productItemId);
        return new ResponseEntity<>(productItemDto,HttpStatus.OK);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<?>getAllProductItemByProduct(@PathVariable Long productId){
        BaseDto<List<ProductItemDto>> productItemDtos=productItemService.getAllProductItemByProduct(productId);
        return new ResponseEntity<>(productItemDtos,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<ProductItemDto>>getAllProductItem(){
        List<ProductItemDto>productItemDtos=productItemService.getAllProductItem();
        if(productItemDtos==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(productItemDtos,HttpStatus.OK);
    }


    @PostMapping("/addVariation")
    public ResponseEntity<?>addVariationsForProduct(@RequestBody List<ProductItemVariationDto>  productItemVariationDtos){
       for(ProductItemVariationDto productItemVariationDto:productItemVariationDtos){

           productItemService.addVariationOptionToProductItem(productItemVariationDto);
       }
        return new ResponseEntity<>("Add successfully",HttpStatus.OK);
    }



}
