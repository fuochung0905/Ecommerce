package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.*;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.exception.NotFoundException;
import com.utc2.it.Ecommerce.exception.ResourceNotFoundException;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {
    private final ProductItemRepository productItemRepository;
    private final ProductRepository productRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final VariationRepository variationRepository;
    private final ProductItemVariationOptionRepository productItemVariationOptionRepository;
    @Override
    public Long createNewProductItem(ProductItemDto productItemDto) throws IOException {
        Product product=productRepository.findById(productItemDto.getProductId()).orElseThrow();
        product.setPrice(productItemDto.getPrice());

        List<ProductItem>productItemList=product.getProductItems();
        for(ProductItem productItem:productItemList){
            if(productItem.getIdColor()==productItemDto.getIdColor()){
                throw new NotFoundException("Product item already exists");
            }
        }
        ProductItem productItem= new ProductItem();
        productItem.setProduct(product);
        productItem.setPrice(productItemDto.getPrice());
        productItem.setQyt_stock(0);
        productItem.setShow(true);
        productItem.setIdColor(productItemDto.getIdColor());
        ProductItem save= productItemRepository.save(productItem);
        return save.getId();
    }
    @Override
    public void addVariationOptionToProductItem(ProductItemVariationDto productItemVariationDto) {

        ProductItem productItem = productItemRepository.findById(productItemVariationDto.getProductItemId()).orElseThrow(() -> new ResourceNotFoundException("product", "productId", productItemVariationDto.getProductItemId()));
        VariationOption variationOption = variationOptionRepository.findById(productItemVariationDto.getVariationOptionId()).orElseThrow(() -> new ResourceNotFoundException("variation", "variationId", productItemVariationDto.getVariationOptionId()));
        List<ProductItemVariationOption> productItemVariationOptions= productItemVariationOptionRepository.findAllByProductItemId(productItem);
        for (ProductItemVariationOption productItemVariationOption : productItemVariationOptions) {
            if(productItemVariationOption.getVariationOption().getId()==variationOption.getId()){
                productItemVariationOptionRepository.delete(productItemVariationOption);
            }
        }
        ProductItemVariationOption productItemVariationOption= new ProductItemVariationOption();
        productItemVariationOption.setProductItem(productItem);
        productItemVariationOption.setVariationOption(variationOption);
        productItemVariationOptionRepository.save(productItemVariationOption);
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
        dto.setImage(productItem.getProductItemImage());
        dto.setQyt_stock(productItem.getQyt_stock());
        dto.setPrice(productItem.getPrice());
        return dto;
    }

    @Override
    public ProductDto getProductItemById(Long productItemId) {
        ProductItem productItem=productItemRepository.findById(productItemId).orElseThrow();
        if(productItem.isShow()){
            Product product=productItem.getProduct();
            if(productItem==null){
                return null;
            }
            ProductDto dto= new ProductDto();
            dto.setId(productItem.getId());
            dto.setProductName(product.getProductName());
            dto.setQuantity(productItem.getQyt_stock());
            dto.setPrice(productItem.getPrice());
            dto.setImage(productItem.getProductItemImage());

            return dto;
        }
        return null;

    }

    @Override
    public ProductDto getProductItemByIsColor(Long productItemId, Long idColor) {
        return null;
    }

    @Override
    public void deleteProductItemById(Long productItemId) {
        ProductItem deleteProductItem= productItemRepository.findById(productItemId).orElseThrow();
        deleteProductItem.setShow(false);
        productItemRepository.save(deleteProductItem);

    }

    @Override
    public List<ProductItemDto> getAllProductItemByProduct(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        List<ProductItem> productItems=productItemRepository.getAllProductItemByProduct(product);
        if(productItems==null){
            return null;
        }
        List<ProductItemDto>productItemDtos=new ArrayList<>();
        for (ProductItem productItem:productItems) {
            if(productItem.isShow()){
                ProductItemDto productItemDto= new ProductItemDto();
                productItemDto.setId(productItem.getId());
                productItemDto.setIdColor(productItem.getIdColor());
                productItemDto.setPrice(productItem.getPrice());
                productItemDto.setQyt_stock(productItem.getQyt_stock());
                productItemDto.setImage(productItem.getProductItemImage());
                productItemDto.setProductId(productItem.getProduct().getId());
                productItemDtos.add(productItemDto);
            }

        }
        return productItemDtos;
    }

    @Override
    public List<ProductItemDto> getAllProductItem() {
        List<ProductItem>productItems= productItemRepository.findAll();
        List<ProductItemDto>productItemDtos= new LinkedList<>();
        if(productItems==null){
            return null;
        }
        for (ProductItem item:productItems) {
            if(item.isShow()){
                ProductItemDto dto= new ProductItemDto();
                dto.setProductId(item.getId());
                dto.setProductId(item.getProduct().getId());
                dto.setIdColor(item.getIdColor());
                dto.setImage(item.getProductItemImage());
                dto.setQyt_stock(item.getQyt_stock());
                dto.setPrice(item.getPrice());
                productItemDtos.add(dto);
            }

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
    public ProductItem getProductItemByProductAndVarationOption(List<ColorSizeDto> productVariationDtos) {

//        List<VariationOption>variationOptions= new LinkedList<>();
//        Product product= new Product();
//        for(ProductVariationDto productVariationDto:productVariationDtos){
//            product=productRepository.findById(productVariationDto.getProductId()).orElseThrow();
//            VariationOption variationOption= new VariationOption();
//            variationOption=variationOptionRepository.findById(productVariationDto.getVariationOptionId()).orElseThrow();
//            variationOptions.add(variationOption);
//        }
//        return productItemRepository.getProductItemByProductAndVariations(variationOptions,product);
        ProductItem productItem = new ProductItem();
        return productItem;
    }


}
