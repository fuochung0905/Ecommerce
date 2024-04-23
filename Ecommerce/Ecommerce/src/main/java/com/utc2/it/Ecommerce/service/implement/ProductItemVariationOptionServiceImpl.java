package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.ProductItemVariationOptionDto;
import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.entity.ProductItem;
import com.utc2.it.Ecommerce.entity.ProductItemVariationOption;
import com.utc2.it.Ecommerce.entity.VariationOption;
import com.utc2.it.Ecommerce.repository.ProductItemRepository;
import com.utc2.it.Ecommerce.repository.ProductItemVariationOptionRepository;
import com.utc2.it.Ecommerce.repository.ProductRepository;
import com.utc2.it.Ecommerce.repository.VariationOptionRepository;
import com.utc2.it.Ecommerce.service.ProductItemVariationOptionService;
import com.utc2.it.Ecommerce.service.VariationOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor


public class ProductItemVariationOptionServiceImpl implements ProductItemVariationOptionService {
    private final ProductItemRepository productItemRepository;
    private final ProductItemVariationOptionRepository productItemVariationOptionRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductItemVariationOptionDto updateProductItemVariationOption(Long id,ProductItemVariationOptionDto productItemVariationOptionDto) {
       ProductItemVariationOption productItemVariationOption=productItemVariationOptionRepository.findById(id).orElseThrow();
        productItemVariationOption.setQuantity(productItemVariationOptionDto.getQuantity());
       ProductItemVariationOption savedProductItemVariationOption=productItemVariationOptionRepository.save(productItemVariationOption);
       ProductItem productItem=productItemRepository.findById(productItemVariationOption.getProductItem().getId()).orElseThrow();
       productItem.setQyt_stock(productItem.getQyt_stock()+productItemVariationOptionDto.getQuantity());
       productItemRepository.save(productItem);
        Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
        product.setQuantity(product.getQuantity()+productItemVariationOptionDto.getQuantity());
        productRepository.save(product);
       ProductItemVariationOptionDto dto=new ProductItemVariationOptionDto();
       dto.setQuantity(savedProductItemVariationOption.getQuantity());
       return dto;
    }

    @Override
    public void deleteProductItemVariationOption(Long productItemVariationOptionId) {
        ProductItemVariationOption productItemVariationOption=productItemVariationOptionRepository.findById(productItemVariationOptionId).orElse(null);
        productItemVariationOptionRepository.delete(productItemVariationOption);
    }

    @Override
    public List<ProductItemVariationOptionDto> getAllProductItemVariationOptionsByProductItem(Long productItemId) {
        ProductItem productItem=productItemRepository.findById(productItemId).orElseThrow();
        List<ProductItemVariationOption>productItemVariationOptions=productItemVariationOptionRepository.findAllByProductItemId(productItem);
        List<ProductItemVariationOptionDto>productItemVariationOptionDtos= new ArrayList<>();
        for(ProductItemVariationOption productItemVariationOption:productItemVariationOptions){

            ProductItemVariationOptionDto productItemVariationOptionDto= new ProductItemVariationOptionDto();
            productItemVariationOptionDto.setId(productItemVariationOption.getId());
            productItemVariationOptionDto.setProductItemId(productItem.getId());
            productItemVariationOptionDto.setIdColor(productItem.getIdColor());
            productItemVariationOptionDto.setVariationOptionId(productItemVariationOption.getId());
            VariationOption variationOption=variationOptionRepository.findById(productItemVariationOption.getVariationOption().getId()).orElseThrow();
            productItemVariationOptionDto.setValue(variationOption.getValue());
            productItemVariationOptionDto.setQuantity(productItemVariationOption.getQuantity());
            productItemVariationOptionDtos.add(productItemVariationOptionDto);
        }
        return productItemVariationOptionDtos;
    }

}
