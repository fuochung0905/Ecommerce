package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.VariationDto;
import com.utc2.it.Ecommerce.entity.Category;
import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.entity.Variation;
import com.utc2.it.Ecommerce.repository.CategoryRepository;
import com.utc2.it.Ecommerce.repository.ProductRepository;
import com.utc2.it.Ecommerce.repository.VariationRepository;
import com.utc2.it.Ecommerce.service.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class VariationServiceImpl implements VariationService {
    private final VariationRepository variationRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public VariationDto createNewVariation(VariationDto dto) {
        Category category=categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        Variation variation=new Variation();
        variation.setName(dto.getName());
        variation.setCategory(category);
        Variation saveVariation=variationRepository.save(variation);
        VariationDto variationDto= new VariationDto();
        variationDto.setId(saveVariation.getId());
        variationDto.setName(saveVariation.getName());
        variationDto.setCategoryId(saveVariation.getCategory().getId());
        return variationDto;
    }

    @Override
    public VariationDto updateVariation(Long variationId, VariationDto variationDto) {
        Variation variation=variationRepository.findById(variationId).orElseThrow();
        variation.setName(variationDto.getName());
        Variation saveVariation=variationRepository.save(variation);
        VariationDto dto= new VariationDto();
        dto.setId(saveVariation.getId());
        dto.setName(saveVariation.getName());
        dto.setCategoryId(saveVariation.getCategory().getId());
        return dto;
    }

    @Override
    public void deleteVariation(Long variationId) {
        Variation variation=variationRepository.findById(variationId).orElseThrow();
        variationRepository.delete(variation);
    }

    @Override
    public VariationDto getVariationById(Long variationId) {
        Variation variation=variationRepository.findById(variationId).orElseThrow();
        VariationDto dto= new VariationDto();
        dto.setId(variation.getId());
        dto.setName(variation.getName());
        dto.setCategoryId(variation.getCategory().getId());
        return dto;

    }

    @Override
    public List<VariationDto> getAllVariation() {
        List<Variation>variations=variationRepository.findAll();
        List<VariationDto>variationDtos= new ArrayList<>();
        for (Variation variation:variations) {
            VariationDto variationDto= new VariationDto();
            variationDto.setId(variation.getId());
            variationDto.setName(variation.getName());
            variationDto.setCategoryId(variation.getCategory().getId());
            variationDtos.add(variationDto);
        }
        return variationDtos;
    }

    @Override
    public List<VariationDto> getVariationByProduct(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        Category category=product.getCategory();
        List<Variation>variations=category.getVariations();
        List<VariationDto>variationDtos= new ArrayList<>();
        for (Variation variation:variations) {
            VariationDto dto= new VariationDto();
            dto.setId(variation.getId());
            dto.setName(variation.getName());
            dto.setCategoryId(variation.getCategory().getId());
            variationDtos.add(dto);
        }
        return variationDtos;
    }

}
