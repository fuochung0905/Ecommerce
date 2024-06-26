package com.utc2.it.Ecommerce.controller;

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

    private static final String UPLOAD_DIR = "src/main/resources/images";
    private String saveImageToDirectory(MultipartFile file)throws IOException{
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream=file.getInputStream()) {
            Path filePath=uploadPath.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }
    }
    @PostMapping("/createNewProductItem")
    public ResponseEntity<?>createNewProductItem( ProductItemDto dto, @RequestParam("file") MultipartFile file)throws IOException {
        Long productItemId= productItemService.createNewProductItem(dto);
        try {
            String fileName=saveImageToDirectory(file);
            productItemService.saveProductItemImage(productItemId,fileName);
            return new ResponseEntity<>("Add product successfully", HttpStatus.CREATED);
        }catch (IOException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload image: " + ex.getMessage());
        }
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
    public ResponseEntity<List<ProductItemDto>>getAllProductItemByProduct(@PathVariable Long productId){
        List<ProductItemDto>productItemDtos=productItemService.getAllProductItemByProduct(productId);
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
