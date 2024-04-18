package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.dto.ProductItemVariationDto;
import com.utc2.it.Ecommerce.dto.VariationOptionDto;
import com.utc2.it.Ecommerce.entity.Category;
import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.entity.VariationOption;
import com.utc2.it.Ecommerce.exception.NotFoundException;
import com.utc2.it.Ecommerce.exception.ResourceNotFoundException;
import com.utc2.it.Ecommerce.repository.CategoryRepository;
import com.utc2.it.Ecommerce.repository.ProductRepository;
import com.utc2.it.Ecommerce.repository.VariationRepository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


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

    private ProductDto getProductDto(ProductDto productDto, Product product) {
        productDto.setId(product.getId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setImage(productDto.getImage());
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
            dto.setImage(product.getImageName());
            productDtos.add(dto);
        }
        return productDtos;
    }

    @Override
    public ProductDto addVariationForProduct(ProductItemVariationDto productVariationDto) {
//        Set<VariationOption>variationSet=null;
//        Product product=productRepository.findById(productVariationDto.getProductId()).orElseThrow(()->new ResourceNotFoundException("product","productId", productVariationDto.getProductId()));
//        VariationOption variation=variationRepository.findById(productVariationDto.getVariationId()).orElseThrow(()->new ResourceNotFoundException("variation","variationId", productVariationDto.getVariationId()));
//        variationSet=product.getVariations();
//        variationSet.add(variation);
//        product.setVariations(variationSet);
//        Product saveProduct=productRepository.save(product);
//        ProductDto productDto=new ProductDto();
//        productDto.setProductName(saveProduct.getProductName());
//        productDto.setDescription(saveProduct.getDescription());
//        productDto.setQuantity(saveProduct.getQuantity());
//        productDto.setExportPrice(saveProduct.getExportPrice());
//        productDto.setImportPrice(saveProduct.getImportPrice());
//
//        productDto.setCategoryId(saveProduct.getCategory().getId());
//        return productDto;
        return null;

    }

    @Override
    public ProductDto removeVariationForProduct(ProductItemVariationDto productVariationDto) {
//        Set<VariationOption>variationSet=null;
//        Product product=productRepository.findById(productVariationDto.getProductId()).orElseThrow(()->new ResourceNotFoundException("product","productId", productVariationDto.getProductId()));
//        VariationOption variation=variationRepository.findById(productVariationDto.getVariationId()).orElseThrow(()->new ResourceNotFoundException("variation","variationId", productVariationDto.getVariationId()));
//        variationSet=product.getVariations();
//        for (VariationOption vari:variationSet) {
//            if(vari==variation){
//                variationSet.remove(vari);
//            }
//        }
//        product.setVariations(variationSet);
//        Product saveProduct=productRepository.save(product);
//        ProductDto productDto=new ProductDto();
//        productDto.setProductName(saveProduct.getProductName());
//        productDto.setDescription(saveProduct.getDescription());
//        productDto.setQuantity(saveProduct.getQuantity());
//        productDto.setExportPrice(saveProduct.getExportPrice());
//        productDto.setImportPrice(saveProduct.getImportPrice());
//        productDto.setCategoryId(saveProduct.getCategory().getId());
//        return productDto;
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
}
