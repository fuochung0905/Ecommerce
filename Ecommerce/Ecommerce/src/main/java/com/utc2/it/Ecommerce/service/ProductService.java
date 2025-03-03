package com.utc2.it.Ecommerce.service;



import com.utc2.it.Ecommerce.Base.BaseDto;
import com.utc2.it.Ecommerce.dto.*;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    BaseDto<ProductDto> addProduct(ProductDto productDto, MultipartFile file)throws IOException;
    Long  createProduct( ProductDto productDto) throws IOException;
    BaseDto<ProductDto> updateProduct(Long productId, ProductDto productDto) throws IOException;
    BaseDto<ProductDto> getProductById(Long productId);
    BaseDto<ProductDto> getProductByProductItemId(Long productItemId);
    BaseDto<DeleteResponse> deleteProductById(Long productId);
    BaseDto<List<ProductDto>> getAllProduct();
    BaseDto<ProductDto> addVariationForProduct(ProductItemVariationDto productVariationDto);
    BaseDto<ProductDto> removeVariationForProduct(ProductItemVariationDto productVariationDto);
    BaseDto<List<VariationOptionDto>> getAllVariationProduct(@Param("productId")Long productId);
    void saveProductImage(Long productId, String imageName);
    BaseDto<CurrentDetailProductDto> getCurrentDetailProduct(Long productId);
    BaseDto<ProductDto> getProductByIsColorAndByVariationOption(Long colorId,Long variationOptionId);
    BaseDto<List<ProductDto>> getAllProductByCategory(Long categoryId);
    BaseDto<List<ProductDto>> getAllProductSearchLikeName(String productName);
}
