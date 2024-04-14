package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.service.VariationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.dto.VariationDto;
import com.utc2.it.Ecommerce.entity.Category;
import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.entity.Variation;
import com.utc2.it.Ecommerce.exception.ResourceNotFoundException;
import com.utc2.it.Ecommerce.repository.CategoryRepository;
import com.utc2.it.Ecommerce.repository.ProductRepository;
import com.utc2.it.Ecommerce.repository.VariationRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationServiceImpl implements VariationService {
    private final VariationRepository variationRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @Override
    public VariationDto createVariationDto(VariationDto dto) {
        VariationDto variationDto= new VariationDto();
        Variation variation= new Variation();
        variation.setId(dto.getId());
        variation.setName(dto.getName());
        variation.setValue(dto.getValue());
        Category category=categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("category","categoryId", dto.getCategoryId()));
        variation.setCategory(category);
        variation=variationRepository.save(variation);
        variation.setCategory(category);
        variationDto.setId(variation.getId());
        variationDto.setName(variation.getName());
        variationDto.setValue(variation.getValue());
        variationDto.setCategoryId(variation.getCategory().getId());
        return  variationDto;
    }

    @Override
    public VariationDto updateVariation(Long variationId, VariationDto dto) {
        Variation variation=variationRepository.findById(variationId).orElseThrow(()->new ResourceNotFoundException("variation","variationId",variationId));
        if(variation!=null){
            variation.setName(dto.getName());
            variation.setValue(dto.getValue());
            Category category=categoryRepository.findById(dto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("category","categoryId", dto.getCategoryId()));
            variation=variationRepository.save(variation);
            variation.setCategory(category);
            VariationDto variationDto= new VariationDto();
            variationDto.setId(variation.getId());
            variationDto.setName(variation.getName());
            variationDto.setValue(variation.getValue());
            variationDto.setCategoryId(variation.getCategory().getId());
            return variationDto;
        }
        return  null;
    }
    @Override
    public void deleteVariation(Long categoryId) {
        Variation variation=variationRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("variation","variationId",categoryId));
        if(variation!=null){
            variationRepository.delete(variation);
        }
    }
    @Override
    public VariationDto getVariationById(Long categoryId) {
        Variation variation=variationRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("variation","variationId",categoryId));
        if(variation!=null){
            VariationDto variationDto= new VariationDto();
            variationDto.setId(variation.getId());
            variationDto.setName(variation.getName());
            variationDto.setValue(variation.getValue());
            variationDto.setCategoryId(variation.getCategory().getId());
            return variationDto;
        }
        return null;

    }

    @Override
    public List<VariationDto> getAllVariation() {
        List<Variation>variations=variationRepository.findAll();
        List<VariationDto>variationDtos= new ArrayList<>();
        for (Variation variation:variations) {
            VariationDto dto= new VariationDto();
            dto.setId(variation.getId());
            dto.setName(variation.getName());
            dto.setValue(variation.getValue());
            dto.setCategoryId(variation.getCategory().getId());
            variationDtos.add(dto);
        }
        return variationDtos;
    }
    @Override
    public List<VariationDto> getAllVariationByCategory(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        List<Variation> variations=variationRepository.getListVariationByCategory(product.getCategory());
        List<VariationDto> variationDtos= new ArrayList<>();
        for (Variation variation:variations) {
            VariationDto variationDto= new VariationDto();
            variationDto.setId(variation.getId());
            variationDto.setName(variation.getName());
            variationDto.setValue(variation.getValue());
            variationDto.setCategoryId(variation.getCategory().getId());
            variationDtos.add(variationDto);
        }
        return variationDtos;
    }

    @Override
    public List<VariationDto> getAllVariationByProduct(Long productId) {
        List<Variation> variations=variationRepository.getListVariationByProduct(productId);
        List<VariationDto> variationDtos= new ArrayList<>();
        for (Variation variation:variations) {
            VariationDto variationDto= new VariationDto();
            variationDto.setId(variation.getId());
            variationDto.setName(variation.getName());
            variationDto.setValue(variation.getValue());
            variationDto.setCategoryId(variation.getCategory().getId());
            variationDtos.add(variationDto);
        }
        return variationDtos;
    }
}
