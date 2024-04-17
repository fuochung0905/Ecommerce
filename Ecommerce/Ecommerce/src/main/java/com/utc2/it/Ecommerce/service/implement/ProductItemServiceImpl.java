package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.dto.ProductItemDto;
import com.utc2.it.Ecommerce.dto.ProductItemVariationDto;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.exception.NotFoundException;
import com.utc2.it.Ecommerce.exception.ResourceNotFoundException;
import com.utc2.it.Ecommerce.repository.ProductItemRepository;
import com.utc2.it.Ecommerce.repository.ProductRepository;
import com.utc2.it.Ecommerce.repository.VariationOptionRepository;
import com.utc2.it.Ecommerce.service.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {
    private final ProductItemRepository productItemRepository;
    private final ProductRepository productRepository;
    private final VariationOptionRepository variationOptionRepository;
    @Override
    public Long createNewProductItem(ProductItemDto productItemDto) throws IOException {
        ProductItem productItem= new ProductItem();
        Product product= productRepository.findById(productItemDto.getProductId()).orElseThrow();
        productItem.setProduct(product);
        productItem.setPrice(productItemDto.getPrice());
        productItem.setQyt_stock(productItemDto.getQyt_stock());
        ProductItem save= productItemRepository.save(productItem);
        return save.getId();
    }

    @Override
    public ProductItemDto updateProductItem(Long productItemId, ProductItemDto productItemDto) throws IOException {
        ProductItem findProductItem=productItemRepository.findById(productItemId).orElseThrow();
        findProductItem.setQyt_stock(productItemDto.getQyt_stock());
        findProductItem.setPrice(productItemDto.getPrice());
        ProductItem productItem=productItemRepository.save(findProductItem);
        ProductItemDto dto= new ProductItemDto();
        dto.setProductId(productItem.getId());
        dto.setProductId(productItem.getProduct().getId());
        dto.setProductItemImage(productItem.getProductItemImage());
        dto.setQyt_stock(productItem.getQyt_stock());
        dto.setPrice(productItem.getPrice());
        return dto;
    }

    @Override
    public ProductItemDto getProductItemById(Long productItemId) {
        ProductItem productItem=productItemRepository.findById(productItemId).orElseThrow();
        ProductItemDto dto= new ProductItemDto();
        dto.setProductId(productItem.getId());
        dto.setProductId(productItem.getProduct().getId());
        dto.setProductItemImage(productItem.getProductItemImage());
        dto.setQyt_stock(productItem.getQyt_stock());
        dto.setPrice(productItem.getPrice());
        return dto;
    }

    @Override
    public void deleteProductItemById(Long productItemId) {
        ProductItem deleteProductItem= productItemRepository.findById(productItemId).orElseThrow();
        productItemRepository.delete(deleteProductItem);

    }

    @Override
    public List<ProductItemDto> getAllProductItemByProduct(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        List<ProductItem> productItems=product.getProductItems();
        List<ProductItemDto>productItemDtos=new ArrayList<>();
        for (ProductItem productItem:productItems) {
            ProductItemDto productItemDto= new ProductItemDto();
            productItemDto.setId(productItem.getId());
            productItemDto.setPrice(productItem.getPrice());
            productItemDto.setQyt_stock(productItem.getQyt_stock());
            productItemDto.setProductItemImage(productItem.getProductItemImage());
            productItemDto.setProductId(productItem.getProduct().getId());
            productItemDtos.add(productItemDto);
        }
        return productItemDtos;
    }

    @Override
    public List<ProductItemDto> getAllProductItem() {
        List<ProductItem>productItems= productItemRepository.findAll();
        List<ProductItemDto>productItemDtos= new LinkedList<>();
        for (ProductItem item:productItems) {
            ProductItemDto dto= new ProductItemDto();
            dto.setProductId(item.getId());
            dto.setProductId(item.getProduct().getId());
            dto.setProductItemImage(item.getProductItemImage());
            dto.setQyt_stock(item.getQyt_stock());
            dto.setPrice(item.getPrice());
            productItemDtos.add(dto);
        }
        return productItemDtos;
    }
    @Override
    public void saveProductItemImage(Long productItemId, String imageName) {
        ProductItem productItem=productItemRepository.findById(productItemId).orElseThrow(()->new NotFoundException("Not found productItem "));
        productItem.setProductItemImage(imageName);
        productItemRepository.save(productItem);
    }

    @Override
    public ProductItemDto addVariationOptionToProductItem(ProductItemVariationDto productItemVariationDto) {Set<VariationOption> variationSet=null;
       ProductItem productItem=productItemRepository.findById(productItemVariationDto.getProductItemId()).orElseThrow(()->new ResourceNotFoundException("product","productId", productItemVariationDto.getProductItemId()));
       VariationOption variation=variationOptionRepository.findById(productItemVariationDto.getVariationOptionId()).orElseThrow(()->new ResourceNotFoundException("variation","variationId", productItemVariationDto.getVariationOptionId()));
        variationSet=productItem.getVariations();
        variationSet.add(variation);
        productItem.setVariations(variationSet);
        ProductItem saveProductItem=productItemRepository.save(productItem);
        ProductItemDto productItemDto=new ProductItemDto();
        productItemDto.setId(saveProductItem.getId());
        productItemDto.setPrice(saveProductItem.getPrice());
        productItemDto.setQyt_stock(saveProductItem.getQyt_stock());
        productItemDto.setProductItemImage(saveProductItem.getProductItemImage());
        productItemDto.setProductId(saveProductItem.getProduct().getId());
        return productItemDto;
    }

    @Override
    public ProductItemDto RemoveVariationOptionToProductItem(ProductItemVariationDto productItemVariationDto) {
        Set<VariationOption>variationSet=null;
        ProductItem productItem=productItemRepository.findById(productItemVariationDto.getProductItemId()).orElseThrow(()->new ResourceNotFoundException("product","productId", productItemVariationDto.getProductItemId()));
        VariationOption variationOption=variationOptionRepository.findById(productItemVariationDto.getVariationOptionId()).orElseThrow(()->new ResourceNotFoundException("variation","variationId", productItemVariationDto.getVariationOptionId()));
        variationSet=productItem.getVariations();
        for (VariationOption vari:variationSet) {
            if(vari==variationOption){
                variationSet.remove(vari);
            }
        }
        productItem.setVariations(variationSet);
        ProductItem saveProductItem=productItemRepository.save(productItem);
        ProductItemDto productItemDto=new ProductItemDto();
        productItemDto.setId(saveProductItem.getId());
        productItemDto.setPrice(saveProductItem.getPrice());
        productItemDto.setQyt_stock(saveProductItem.getQyt_stock());
        productItemDto.setProductItemImage(saveProductItem.getProductItemImage());
        productItemDto.setProductId(saveProductItem.getProduct().getId());
        return productItemDto;
    }
}
