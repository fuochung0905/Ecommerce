package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.*;
import com.utc2.it.Ecommerce.entity.ProductItem;

import java.io.IOException;
import java.util.List;
public interface ProductItemService {
    Long  createNewProductItem( ProductItemDto productItemDto) throws IOException;
     void addVariationOptionToProductItem(ProductItemVariationDto productItemVariationDto);
    ProductItemDto updateProductItem(Long productItemId, ProductItemDto productItemDto) throws IOException;
    ProductDto getProductItemById(Long productItemId);
    void deleteProductItemById(Long productItemId);
    List<ProductItemDto> getAllProductItemByProduct(Long productId);
    List<ProductItemDto> getAllProductItem();
    void saveProductItemImage(Long productItemId, String imageName);
    ProductItem getProductItemByProductAndVarationOption(List<ProductVariationDto>productVariationDtos);
}
