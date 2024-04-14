package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.dto.ProductVariationDto;
import com.utc2.it.Ecommerce.dto.VariationDto;
import com.utc2.it.Ecommerce.entity.Category;
import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.entity.Variation;
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
    private final VariationRepository variationRepository;


    @Override
    public Long createProduct(ProductDto productDto) throws IOException {
        Product product= new Product();
        ProductDto dto= new ProductDto();
        Category category= categoryRepository.findById(productDto.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("category","categoryId",productDto.getCategoryId()));
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setExportPrice(productDto.getExportPrice());
        product.setImportPrice(productDto.getImportPrice());
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
            product.setQuantity(dto.getQuantity());
            product.setExportPrice(dto.getExportPrice());
            product.setImportPrice(dto.getImportPrice());
//            product.setImage(dto.getImage().getBytes());
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
        productDto.setImportPrice(product.getImportPrice());
        productDto.setExportPrice(product.getExportPrice());
        productDto.setQuantity(product.getQuantity());


        productDto.setCategoryId(product.getCategory().getId());
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
            dto.setImportPrice(product.getImportPrice());
            dto.setExportPrice(product.getExportPrice());
            productDtos.add(dto);
        }
        return productDtos;
    }

    @Override
    public ProductDto addVariationForProduct(ProductVariationDto productVariationDto) {
        Set<Variation>variationSet=null;
        Product product=productRepository.findById(productVariationDto.getProductId()).orElseThrow(()->new ResourceNotFoundException("product","productId", productVariationDto.getProductId()));
        Variation variation=variationRepository.findById(productVariationDto.getVariationId()).orElseThrow(()->new ResourceNotFoundException("variation","variationId", productVariationDto.getVariationId()));
        variationSet=product.getVariations();
        variationSet.add(variation);
        product.setVariations(variationSet);
        Product saveProduct=productRepository.save(product);
        ProductDto productDto=new ProductDto();
        productDto.setProductName(saveProduct.getProductName());
        productDto.setDescription(saveProduct.getDescription());
        productDto.setQuantity(saveProduct.getQuantity());
        productDto.setExportPrice(saveProduct.getExportPrice());
        productDto.setImportPrice(saveProduct.getImportPrice());

        productDto.setCategoryId(saveProduct.getCategory().getId());
        return productDto;

    }

    @Override
    public ProductDto removeVariationForProduct(ProductVariationDto productVariationDto) {
        Set<Variation>variationSet=null;
        Product product=productRepository.findById(productVariationDto.getProductId()).orElseThrow(()->new ResourceNotFoundException("product","productId", productVariationDto.getProductId()));
        Variation variation=variationRepository.findById(productVariationDto.getVariationId()).orElseThrow(()->new ResourceNotFoundException("variation","variationId", productVariationDto.getVariationId()));
        variationSet=product.getVariations();
        for (Variation vari:variationSet) {
            if(vari==variation){
                variationSet.remove(vari);
            }
        }
        product.setVariations(variationSet);
        Product saveProduct=productRepository.save(product);
        ProductDto productDto=new ProductDto();
        productDto.setProductName(saveProduct.getProductName());
        productDto.setDescription(saveProduct.getDescription());
        productDto.setQuantity(saveProduct.getQuantity());
        productDto.setExportPrice(saveProduct.getExportPrice());
        productDto.setImportPrice(saveProduct.getImportPrice());
        productDto.setCategoryId(saveProduct.getCategory().getId());
        return productDto;
    }

    @Override
    public List<VariationDto> getAllVariationProduct(Long productId) {

        return null;
    }

    @Override
    public void saveProductImage(Long productId, String imageName) {
        Product product=productRepository.findById(productId).orElseThrow(()->new NotFoundException("Not found product "));
        product.setImageName(imageName);
        productRepository.save(product);
    }
}
