package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.Base.BaseDto;
import com.utc2.it.Ecommerce.dto.*;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.exception.NotFoundException;
import com.utc2.it.Ecommerce.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductItemRepository productItemRepository;
    private final VariationOptionRepository variationOptionRepository;
    private static final String UPLOAD_DIR = "src/main/resources/images";
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
    public BaseDto<ProductDto> addProduct(ProductDto productDto, MultipartFile file) throws IOException {
        BaseDto<ProductDto> response= new BaseDto<ProductDto>();
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream=file.getInputStream()) {
            Path filePath=uploadPath.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e){
            response.setMessage("Thêm ảnh thất bại");
            response.setSuccess(false);
            return response;
        }
        Product product= new Product();
        Category category= categoryRepository.findByCategoryIdNoDelete(productDto.getCategoryId());
        if(category == null){
            response.setMessage("Category not found");
            response.setSuccess(false);
            return response;
        }
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setImageName(fileName);
        product = productRepository.save(product);
        ProductDto dataResponse = getProductDto(productDto, product);
        response.setSuccess(true);
        response.setData(dataResponse);
        response.setMessage("Product create successfully");
        return response;
    }
    @Override
    public BaseDto<ProductDto> updateProduct(Long productId, ProductDto dto) throws IOException {
        BaseDto<ProductDto> response = new BaseDto<ProductDto>();
        Product product= productRepository.findByProductIdNoDelete(productId);
        if(product == null){
            response.setMessage("Product not found");
            response.setSuccess(false);
            return response;
        }
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        Product saveProduct=productRepository.save(product);
        ProductDto productDto= new ProductDto();
        ProductDto dataResponse = getProductDto(productDto, saveProduct);
        response.setSuccess(true);
        response.setData(dataResponse);
        response.setMessage("Product updated successfully");
        return response;
    }

    @Override
    public BaseDto<ProductDto> getProductById(Long productId) {
        BaseDto<ProductDto> response = new BaseDto<ProductDto>();

        Product product= productRepository.findByProductIdNoDelete(productId);
        if(product ==null) {
           response.setMessage("Product not found");
           response.setSuccess(false);
           return response;
        }
        ProductDto productDto = new ProductDto();
        ProductDto dataResponse = getProductDto(productDto, product);
        response.setSuccess(true);
        response.setMessage("Product get successfully");
        response.setData(dataResponse);
        return response;
    }

    @Override
    public BaseDto<ProductDto> getProductByProductItemId(Long productItemId) {
        BaseDto<ProductDto> response =  new BaseDto<ProductDto>();
        ProductItem productItem=productItemRepository.findById(productItemId).orElseThrow();
        Product product=productRepository.findByProductIdNoDelete(productItem.getProduct().getId());

        if(product ==null ) {
          response.setMessage("Product not found");
          response.setSuccess(false);
          return response;
        }
        ProductDto dataResponse = new ProductDto();
        dataResponse = getProductDto(dataResponse, product);
        response.setSuccess(true);
        response.setMessage("Product get successfully");
        response.setData(dataResponse);
        return response;
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
    public BaseDto<DeleteResponse> deleteProductById(Long productId) {
        BaseDto<DeleteResponse> response = new BaseDto<DeleteResponse>();
        Product product= productRepository.findByProductIdNoDelete(productId);
        if(product ==null ) {
            response.setMessage("Product not found");
            response.setSuccess(false);
            return response;
        }
        product.setDeleted(true);
        productRepository.save(product);
        DeleteResponse deleteResponse = new DeleteResponse("Product deleted successfully"+ product.getProductName());
        response.setMessage("Product deleted successfully");
        response.setData(deleteResponse);
        response.setSuccess(true);
        return response;

    }

    @Override
    public BaseDto<List<ProductDto>> getAllProduct() {
        BaseDto<List<ProductDto>> response = new BaseDto<List<ProductDto>>();
        List<Product>products = productRepository.findByProductAllNoDelete();
        if(products.isEmpty()){
            response.setMessage("No products in list");
            response.setSuccess(true);
            return response;
        }
        List<ProductDto> productDtos =  new ArrayList<>();
        for (Product product : products) {
                ProductDto dto= new ProductDto();
                dto.setId(product.getId());
                dto.setProductName(product.getProductName());
                dto.setDescription(product.getDescription());
                dto.setQuantity(product.getQuantity());
                dto.setPrice(product.getPrice());
                dto.setImage(product.getImageName());
                productDtos.add(dto);
        }
        response.setSuccess(true);
        response.setData(productDtos);
        response.setMessage("Get list product successfully");
        return response;
    }

    @Override
    public BaseDto<ProductDto> addVariationForProduct(ProductItemVariationDto productVariationDto) {
        return null;
    }

    @Override
    public BaseDto<ProductDto> removeVariationForProduct(ProductItemVariationDto productVariationDto) {
        return null;
    }

    @Override
    public BaseDto<List<VariationOptionDto>> getAllVariationProduct(Long productId) {
        return null;
    }

    @Override
    public void saveProductImage(Long productId, String imageName) {
        Product product=productRepository.findById(productId).orElseThrow(()->new NotFoundException("Not found product "));
        product.setImageName(imageName);
        productRepository.save(product);
    }

    @Override
    public BaseDto<CurrentDetailProductDto> getCurrentDetailProduct(Long productId) {
        BaseDto<CurrentDetailProductDto> response = new BaseDto<CurrentDetailProductDto>();
        Product product = productRepository.findByProductIdNoDelete(productId);
        if(product ==null ) {
            response.setMessage("Product not found");
            response.setSuccess(false);
            return response;
        }
        CurrentDetailProductDto currentDetailProductDto=new CurrentDetailProductDto();
        currentDetailProductDto.setProductId(product.getId());
        currentDetailProductDto.setQuantity(product.getQuantity());
        currentDetailProductDto.setPrice(product.getPrice());
        currentDetailProductDto.setImage(product.getImageName());
        response.setMessage("Product get successfully");
        response.setData(currentDetailProductDto);
        response.setSuccess(true);
        return response;
    }

    @Override
    public BaseDto<ProductDto> getProductByIsColorAndByVariationOption(Long colorId,Long variationOptionId) {
        BaseDto<ProductDto> response = new BaseDto<ProductDto>();
        ProductItem productItem = productItemRepository.findById(colorId).orElseThrow();
        VariationOption variationOption=variationOptionRepository.findById(variationOptionId).orElseThrow();
        ProductItemVariationOption productItemVariationOption=productItemVariationOptionRepository.findProductItemVariationOptionByProductItemAndVariationOption(productItem,variationOption);
        ProductDto productDto= new ProductDto();
        productDto.setId(productItem.getId());
        Product product=productRepository.findByProductIdNoDelete(productItem.getProduct().getId());
        if(product ==null ) {
            response.setMessage("Product not found");
            response.setSuccess(false);
            return response;
        }
        productDto.setPrice(productItem.getPrice());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setQuantity(productItemVariationOption.getQuantity());
        productDto.setImage(productItem.getProductItemImage());
        response.setMessage("Get product successfully");
        response.setData(productDto);
        response.setSuccess(true);
        return response ;
    }

    @Override
    public BaseDto<List<ProductDto>> getAllProductByCategory(Long categoryId) {
        BaseDto<List<ProductDto>> response = new BaseDto<List<ProductDto>>();
        Category category=categoryRepository.findByCategoryIdNoDelete(categoryId);
        if(category==null ) {
            response.setMessage("Category not found");
            response.setSuccess(false);
            return response;
        }
       List<Product>products=productRepository.findAllByCategory(category);
       List<ProductDto>productDtos=new LinkedList<>();
       for (Product product:products) {
           if(!product.isDeleted()){
               ProductDto dto= new ProductDto();
               dto.setId(product.getId());
               dto.setProductName(product.getProductName());
               dto.setDescription(product.getDescription());
               dto.setQuantity(product.getQuantity());
               dto.setPrice(product.getPrice());
               dto.setImage(product.getImageName());
               productDtos.add(dto);
           }
       }
       response.setSuccess(true);
       response.setMessage("Get product successfully");
       response.setData(productDtos);
       return response;
    }

    @Override
    public BaseDto<List<ProductDto>> getAllProductSearchLikeName(String productName) {
        BaseDto<List<ProductDto>> response = new BaseDto<List<ProductDto>>();
        List<Product>products=productRepository.findAllBySearchLikeProductName(productName);
        if(products.isEmpty()){
            response.setMessage("list empty");
            response.setSuccess(false);
            return response;
        }
        List<ProductDto>productDtos=new LinkedList<>();
        for (Product product:products) {
            if(product.isDeleted()){
                ProductDto dto= new ProductDto();
                dto.setId(product.getId());
                dto.setProductName(product.getProductName());
                dto.setDescription(product.getDescription());
                dto.setQuantity(product.getQuantity());
                dto.setPrice(product.getPrice());
                dto.setImage(product.getImageName());
                productDtos.add(dto);
            }
        }
        response.setSuccess(true);
        response.setMessage("Get product successfully");
        response.setData(productDtos);
        return response;
    }
}
