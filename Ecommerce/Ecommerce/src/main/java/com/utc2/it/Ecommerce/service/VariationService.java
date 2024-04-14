package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.VariationDto;
import java.util.List;

public interface VariationService {
    VariationDto createVariationDto(VariationDto dto);
    VariationDto updateVariation(Long variationId,VariationDto dto);
    void deleteVariation(Long categoryId);
    VariationDto getVariationById(Long categoryId);
    List<VariationDto>getAllVariation();
    List<VariationDto>getAllVariationByCategory(Long productId);
    List<VariationDto>getAllVariationByProduct(Long productId);

}
