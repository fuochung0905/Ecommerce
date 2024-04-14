package com.utc2.it.Ecommerce.service;



import org.springframework.data.repository.query.Param;
import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.dto.ProductVariationDto;
import com.utc2.it.Ecommerce.dto.VariationDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Long  createProduct( ProductDto productDto) throws IOException;
    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;
    ProductDto getProductById(Long productId);
    void deleteProductById(Long productId);
    List<ProductDto>getAllProduct();
    ProductDto addVariationForProduct(ProductVariationDto productVariationDto);
    ProductDto removeVariationForProduct(ProductVariationDto productVariationDto);
    List<VariationDto>getAllVariationProduct(@Param("productId")Long productId);
    void saveProductImage(Long productId, String imageName);
}
