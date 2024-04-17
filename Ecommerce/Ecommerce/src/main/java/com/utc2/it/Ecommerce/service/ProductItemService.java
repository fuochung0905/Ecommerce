package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.ProductItemDto;
import com.utc2.it.Ecommerce.dto.ProductItemVariationDto;
import java.io.IOException;
import java.util.List;
public interface ProductItemService {
    Long  createNewProductItem( ProductItemDto productItemDto) throws IOException;
    ProductItemDto updateProductItem(Long productItemId, ProductItemDto productItemDto) throws IOException;
    ProductItemDto getProductItemById(Long productItemId);
    void deleteProductItemById(Long productItemId);
    List<ProductItemDto> getAllProductItemByProduct(Long productId);
    List<ProductItemDto> getAllProductItem();
    void saveProductItemImage(Long productItemId, String imageName);
    ProductItemDto addVariationOptionToProductItem(ProductItemVariationDto productItemVariationDto );
    ProductItemDto RemoveVariationOptionToProductItem(ProductItemVariationDto productItemVariationDto );
}
