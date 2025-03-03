package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.Base.BaseDto;
import com.utc2.it.Ecommerce.dto.*;
import com.utc2.it.Ecommerce.entity.ProductItem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface ProductItemService {
    BaseDto<ProductItemDto> addProductItem(ProductItemDto productDto, MultipartFile file)throws IOException;
    Long  createNewProductItem( ProductItemDto productItemDto) throws IOException;
     void addVariationOptionToProductItem(ProductItemVariationDto productItemVariationDto);
    ProductItemDto updateProductItem(Long productItemId, ProductItemDto productItemDto) throws IOException;
    ProductDto getProductItemById(Long productItemId);
    ProductDto getProductItemByIsColor(Long productItemId,Long idColor);
    void deleteProductItemById(Long productItemId);
    BaseDto<List<ProductItemDto>> getAllProductItemByProduct(Long productId);
    List<ProductItemDto> getAllProductItem();
    void saveProductItemImage(Long productItemId, String imageName);
    ProductItem getProductItemByProductAndVarationOption(List<ColorSizeDto>productVariationDtos);
}
