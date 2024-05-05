package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.*;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.exception.NotFoundException;
import com.utc2.it.Ecommerce.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductItemVariationOptionRepository productItemVariationOptionRepository;
    @Override
    public Long createProduct(ProductDto productDto) throws IOException {
        Product product= new Product();
        Category category= categoryRepository.findById(productDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("category","categoryId",productDto.getCategoryId()));
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product= productRepository.save(product);
        return product.getId();
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto dto) throws IOException {

        Product product= productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("product","productId",productId));

        if(product!=null){
            product.setProductName(dto.getProductName());
            product.setDescription(dto.getDescription());
            Product saveProduct=productRepository.save(product);
            ProductDto productDto= new ProductDto();

            return getProductDto(productDto, saveProduct);

        }
        return null;

    }

    @Override
    public ProductDto getProductById(Long productId) {
        ProductDto productDto = new ProductDto();
        Product product= productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("product","productId",productId));
        if(product!=null) {
            return getProductDto(productDto, product);
        }
        return null;
    }

    @Override
    public ProductDto getProductByProductItemId(Long productItemId) {
        ProductItem productItem=productItemRepository.findById(productItemId).orElseThrow();
        Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
        ProductDto productDto = new ProductDto();
        if(product!=null) {
            return getProductDto(productDto, product);
        }
        return null;
    }

    private ProductDto getProductDto(ProductDto productDto, Product product) {
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setQuantity(product.getQuantity());
        productDto.setPrice(product.getPrice());
        productDto.setImage(product.getImageName());
        return productDto;
    }

    @Override
    public void deleteProductById(Long productId) {
        Product product= productRepository.findById(productId).orElseThrow(()->
                new ResourceNotFoundException("product","productId",productId));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDto> getAllProduct() {
        List<Product>products=productRepository.findAll();
        List<ProductDto>productDtos= new LinkedList();
        for (Product product:products) {
            ProductDto dto= new ProductDto();
            dto.setId(product.getId());
            dto.setProductName(product.getProductName());
            dto.setDescription(product.getDescription());
            dto.setQuantity(product.getQuantity());
            dto.setPrice(product.getPrice());
            dto.setImage(product.getImageName());
            productDtos.add(dto);
        }
        return productDtos;
    }

    @Override
    public ProductDto addVariationForProduct(ProductItemVariationDto productVariationDto) {
        return null;

    }

    @Override
    public ProductDto removeVariationForProduct(ProductItemVariationDto productVariationDto) {
        return null;
    }

    @Override
    public List<VariationOptionDto> getAllVariationProduct(Long productId) {

        return null;
    }

    @Override
    public void saveProductImage(Long productId, String imageName) {
        Product product=productRepository.findById(productId).orElseThrow(()->new NotFoundException("Not found product "));
        product.setImageName(imageName);
        productRepository.save(product);
    }

    @Override
    public CurrentDetailProductDto getCurrentDetailProduct(Long productId) {
        Product product=productRepository.findById(productId).orElseThrow();
        CurrentDetailProductDto currentDetailProductDto=new CurrentDetailProductDto();
        currentDetailProductDto.setProductId(product.getId());
        currentDetailProductDto.setQuantity(product.getQuantity());
        currentDetailProductDto.setPrice(product.getPrice());
        currentDetailProductDto.setImage(product.getImageName());
        return currentDetailProductDto;
    }

    @Override
    public ProductDto getProductByIsColorAndByVariationOption(Long colorId,Long variationOptionId) {
        ProductItem productItem=productItemRepository.findById(colorId).orElseThrow();
        VariationOption variationOption=variationOptionRepository.findById(variationOptionId).orElseThrow();
        ProductItemVariationOption productItemVariationOption=productItemVariationOptionRepository.findProductItemVariationOptionByProductItemAndVariationOption(productItem,variationOption);
        ProductDto productDto= new ProductDto();
        productDto.setId(productItem.getId());
        Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
        productDto.setPrice(productItem.getPrice());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setCategoryId(product.getCategory().getId());

        productDto.setQuantity(productItemVariationOption.getQuantity());
        productDto.setImage(productItem.getProductItemImage());
        return productDto;
    }

    @Override
    public List<ProductDto> getAllProductByCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow();
       List<Product>products=productRepository.findAllByCategory(category);
       List<ProductDto>productDtos=new LinkedList<>();
       for (Product product:products) {
           ProductDto dto= new ProductDto();
           dto.setId(product.getId());
           dto.setProductName(product.getProductName());
           dto.setDescription(product.getDescription());
           dto.setQuantity(product.getQuantity());
           dto.setPrice(product.getPrice());
           dto.setImage(product.getImageName());
           productDtos.add(dto);

       }
       return productDtos;
    }
}
