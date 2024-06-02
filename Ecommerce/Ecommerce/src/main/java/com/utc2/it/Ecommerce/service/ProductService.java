package com.utc2.it.Ecommerce.service;



import com.utc2.it.Ecommerce.dto.*;
import org.springframework.data.repository.query.Param;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Long  createProduct( ProductDto productDto) throws IOException;
    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;
    ProductDto getProductById(Long productId);
    ProductDto getProductByProductItemId(Long productItemId);
    void deleteProductById(Long productId);
    List<ProductDto>getAllProduct();
    ProductDto addVariationForProduct(ProductItemVariationDto productVariationDto);
    ProductDto removeVariationForProduct(ProductItemVariationDto productVariationDto);
    List<VariationOptionDto>getAllVariationProduct(@Param("productId")Long productId);
    void saveProductImage(Long productId, String imageName);
    CurrentDetailProductDto getCurrentDetailProduct(Long productId);
    ProductDto getProductByIsColorAndByVariationOption(Long colorId,Long variationOptionId);
    List<ProductDto>getAllProductByCategory(Long categoryId);
    List<ProductDto>getAllProductSearchLikeName(String productName);
}
