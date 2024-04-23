package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.VariationOptionDto;
import java.util.List;

public interface VariationOptionService {
    VariationOptionDto createVariationDto(VariationOptionDto dto);
    VariationOptionDto updateVariation(Long variationOptionId, VariationOptionDto dto);
    void deleteVariationOption(Long variationOptionId);
    VariationOptionDto getVariationById(Long variationOptionId);
    List<VariationOptionDto>getAllVariation();
    List<VariationOptionDto>getAllVariationByProduct(Long productId);
    List<VariationOptionDto>getAllVariationOptionWithSizeByProduct(Long productId);
    List<VariationOptionDto>getAllVariationOptionWithByProduct(Long productId);
    List<VariationOptionDto>getAllVariationOptionWitColorByProduct(Long productId);
    List<VariationOptionDto>getAllVariationOptionWithSizeByProductItem(Long productItemId);
}
