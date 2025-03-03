package com.utc2.it.Ecommerce.service;



import com.utc2.it.Ecommerce.dto.ProductItemVariationOptionDto;

import java.util.List;

public interface ProductItemVariationOptionService {
   ProductItemVariationOptionDto updateProductItemVariationOption(Long id,ProductItemVariationOptionDto productItemVariationOptionDto);
    void deleteProductItemVariationOption(Long productItemVariationOptionId);
  List<ProductItemVariationOptionDto> getAllProductItemVariationOptionsByProductItem(Long productItemId);

}
