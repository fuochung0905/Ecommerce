package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.VariationOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.dto.VariationOptionDto;
import com.utc2.it.Ecommerce.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VariationOptionServiceImpl implements VariationOptionService {
    private final VariationOptionRepository variationOptionRepository;
    private final ProductItemRepository productItemRepository;
    private final VariationRepository variationRepository;
    private final ProductRepository productRepository;

    @Override
    public VariationOptionDto createVariationDto(VariationOptionDto dto) {
        VariationOptionDto variationDto= new VariationOptionDto();
        VariationOption variationOption= new VariationOption();
        Variation variation= variationRepository.findById(dto.getVariationId()).orElseThrow();
        variationOption.setId(dto.getId());
        variationOption.setValue(dto.getValue());
        variationOption.setVariation(variation);
       VariationOption save=variationOptionRepository.save(variationOption);
        variationDto.setId(save.getId());
        variationDto.setValue(save.getValue());
        variationDto.setVariationId(save.getVariation().getId());
        return  variationDto;
    }

    @Override
    public VariationOptionDto updateVariation(Long variationOptionId, VariationOptionDto dto) {
        VariationOption variationOption=variationOptionRepository.findById(variationOptionId).orElseThrow(()->new ResourceNotFoundException("variation","variationId",variationOptionId));
        if(variationOption!=null){
            variationOption.setValue(dto.getValue());
            VariationOption save=variationOptionRepository.save(variationOption);
            VariationOptionDto variationDto= new VariationOptionDto();
            variationDto.setId(save.getId());
            variationDto.setValue(save.getValue());
            variationDto.setVariationId(save.getVariation().getId());
            return variationDto;
        }
        return  null;
    }
    @Override
    public void deleteVariationOption(Long variationOptionId) {
        VariationOption variationOption=variationOptionRepository.findById(variationOptionId).orElseThrow(()->new ResourceNotFoundException("variation","variationId",variationOptionId));
        if(variationOption!=null){
            variationOptionRepository.delete(variationOption);
        }
    }
    @Override
    public VariationOptionDto getVariationById(Long variationOptionId) {
        VariationOption variationOption=variationOptionRepository.findById(variationOptionId).orElseThrow(()->new ResourceNotFoundException("variation","variationId",variationOptionId));
        if(variationOption!=null){
            VariationOptionDto variationDto= new VariationOptionDto();
            variationDto.setId(variationOption.getId());
            variationDto.setValue(variationOption.getValue());
            return variationDto;
        }
        return null;
    }
    @Override
    public List<VariationOptionDto> getAllVariation() {
        List<VariationOption>variations=variationOptionRepository.findAll();
        List<VariationOptionDto>variationDtos= new ArrayList<>();
        for (VariationOption variation:variations) {
            VariationOptionDto dto= new VariationOptionDto();
            dto.setId(variation.getId());
            dto.setValue(variation.getValue());
            variationDtos.add(dto);
        }
        return variationDtos;
    }

    @Override
    public List<VariationOptionDto> getAllVariationByProduct(Long productId) {
        ProductItem productItem=productItemRepository.findById(productId).orElseThrow();
        Product product=productItem.getProduct();
        Category category=product.getCategory();
        List<Variation>variations=category.getVariations();
        List<VariationOptionDto>variationOptionDtos= new ArrayList<>();
        for (Variation variation:variations) {
            for (VariationOption variationOption:variation.getVariationOptions()) {
                VariationOptionDto dto= new VariationOptionDto();
                dto.setId(variationOption.getId());
                dto.setVariationId(variationOption.getVariation().getId());
                dto.setValue(variationOption.getValue());
                variationOptionDtos.add(dto);
            }
        }
        return variationOptionDtos;
    }

    @Override
    public List<VariationOptionDto> getAllVariationOptionWithSizeByProduct(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        Category category=product.getCategory();
        List<Variation>variations=category.getVariations();
        List<VariationOptionDto>variationOptionDtos= new ArrayList<>();
        for (Variation variation:variations) {
            if(variation.getName()=="size"){
                for (VariationOption variationOption:variation.getVariationOptions()) {
                    VariationOptionDto dto= new VariationOptionDto();
                    dto.setId(variationOption.getId());
                    dto.setVariationId(variationOption.getVariation().getId());
                    dto.setValue(variationOption.getValue());
                    variationOptionDtos.add(dto);
                }
            }
        }
        return variationOptionDtos;
    }
    @Override
    public List<VariationOptionDto> getAllVariationOptionWithColorByProduct(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        Category category=product.getCategory();
        List<Variation>variations=category.getVariations();
        List<VariationOptionDto>variationOptionDtos= new ArrayList<>();
        for (Variation variation:variations) {
            if(variation.getName()=="color"){
                for (VariationOption variationOption:variation.getVariationOptions()) {
                    VariationOptionDto dto= new VariationOptionDto();
                    dto.setId(variationOption.getId());
                    dto.setVariationId(variationOption.getVariation().getId());
                    dto.setValue(variationOption.getValue());
                    variationOptionDtos.add(dto);
                }
            }
        }
        return variationOptionDtos;
    }
    @Override
    public List<VariationOptionDto> getAllVariationOptionByVariation(Long variationId) {
        Variation variation=variationRepository.findById(variationId).orElseThrow();
        List<VariationOption>variationOptions=variationOptionRepository.getAllVariationByVariation(variation);
        List<VariationOptionDto>variationOptionDtos= new ArrayList<>();
        for (VariationOption variationOption:variationOptions) {
            VariationOptionDto variationOptionDto= new VariationOptionDto();
            variationOptionDto.setId(variationOption.getId());
            variationOptionDto.setVariationId(variationOption.getVariation().getId());
            variationOptionDto.setValue(variationOption.getValue());
            variationOptionDtos.add(variationOptionDto);
        }
        return variationOptionDtos;
    }

}
