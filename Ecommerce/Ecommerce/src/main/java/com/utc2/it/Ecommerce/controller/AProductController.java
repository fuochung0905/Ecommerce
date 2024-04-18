package com.utc2.it.Ecommerce.controller;

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

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
public class AProductController {
    private final ProductService productService;
    private static final String UPLOAD_DIR = "src/main/resources/images";
    @PostMapping("/createNewProduct")
    public ResponseEntity<?>createProduct( ProductDto dto,@RequestParam("file")MultipartFile file) throws IOException {

        Long productId=productService.createProduct(dto);
        try {
            String fileName=saveImageToDirectory(file);
            productService.saveProductImage(productId,fileName);
            return new ResponseEntity<>("Add product successfully", HttpStatus.CREATED);
        }catch (IOException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload image: " + ex.getMessage());
        }
    }
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
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable Long productId){
        ProductDto productDto= productService.getProductById(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @PostMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductDto>updateProduct(@RequestBody ProductDto dto,@PathVariable Long productId) throws IOException {
        ProductDto productDto=productService.updateProduct(productId,dto);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<DeleteResponse>deleteProduct(@PathVariable Long productId){
        productService.deleteProductById(productId);
        return new ResponseEntity<>(new DeleteResponse("Product is delete successfully"),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>>getAllProduct(){
        List<ProductDto>productDtos=productService.getAllProduct();
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }
    @PutMapping("/addVariation")
    public ResponseEntity<ProductDto>addVariationToProduct(@RequestBody ProductItemVariationDto productVariationDto){
        ProductDto dto=productService.addVariationForProduct(productVariationDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PutMapping("/removeVariation")
    public ResponseEntity<ProductDto>removeVariationToProduct(@RequestBody ProductItemVariationDto productVariationDto){
        ProductDto dto=productService.removeVariationForProduct(productVariationDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}
