package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.VariationDto;

import java.util.List;

public interface VariationService {
    VariationDto createNewVariation(VariationDto dto);
    VariationDto updateVariation(Long variationId, VariationDto variationDto);
    void deleteVariation(Long variationId);
    VariationDto getVariationById(Long variationId);
    List<VariationDto>getAllVariation();
    List<VariationDto>getVariationByProduct(Long productId);
    List<VariationDto>getVariationUserByProduct(Long productId);
    List<VariationDto>getVariationCategory(Long categoryId);
}
