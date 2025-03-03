package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.Base.BaseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.utc2.it.Ecommerce.dto.DeleteResponse;
import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.dto.ProductItemVariationDto;
import com.utc2.it.Ecommerce.service.ProductService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class AProductController {
    private final ProductService productService;
    private static final String UPLOAD_DIR = "src/main/resources/images";
    @PostMapping("/createNewProduct")
    public ResponseEntity<?>createProduct(@Valid ProductDto dto,@RequestParam("file")MultipartFile file) throws IOException {
       BaseDto<ProductDto> productDtoBaseDto = productService.addProduct(dto,file);
        return new ResponseEntity<>(productDtoBaseDto, HttpStatus.CREATED);
    }
    private String saveImageToDirectory(MultipartFile file)throws IOException{
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
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
    @GetMapping("/{productId}")
    public ResponseEntity<?>getProductById(@PathVariable Long productId){
        BaseDto<ProductDto> productDto= productService.getProductById(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @PostMapping("/updateProduct/{productId}")
    public ResponseEntity<?>updateProduct(@RequestBody ProductDto dto,@PathVariable Long productId) throws IOException {
        BaseDto<ProductDto> productDto=productService.updateProduct(productId,dto);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?>deleteProduct(@PathVariable Long productId){
        BaseDto<DeleteResponse> responseBaseDto = productService.deleteProductById(productId);
        return new ResponseEntity<>(responseBaseDto,HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<?>getAllProduct(){
        BaseDto<List<ProductDto>> productDtos=productService.getAllProduct();
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }
    @PutMapping("/addVariation")
    public ResponseEntity<?>addVariationToProduct(@RequestBody ProductItemVariationDto productVariationDto){
        BaseDto<ProductDto> dto=productService.addVariationForProduct(productVariationDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PutMapping("/removeVariation")
    public ResponseEntity<?>removeVariationToProduct(@RequestBody ProductItemVariationDto productVariationDto){
        BaseDto<ProductDto> dto=productService.removeVariationForProduct(productVariationDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @GetMapping("/productItem/{productItemId}")
    public ResponseEntity<?>getProductByProductItem(@PathVariable Long productItemId){
        BaseDto<ProductDto> productDto=productService.getProductByProductItemId(productItemId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
}
