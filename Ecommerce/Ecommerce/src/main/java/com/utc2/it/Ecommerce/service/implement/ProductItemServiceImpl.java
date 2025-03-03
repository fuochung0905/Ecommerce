package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.Base.BaseDto;
import com.utc2.it.Ecommerce.dto.*;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.exception.NotFoundException;
import com.utc2.it.Ecommerce.exception.ResourceNotFoundException;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
public class ProductItemServiceImpl implements ProductItemService {
    private final ProductItemRepository productItemRepository;
    private final ProductRepository productRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final VariationRepository variationRepository;
    private final ProductItemVariationOptionRepository productItemVariationOptionRepository;
    private static final String UPLOAD_DIR = "src/main/resources/images";
    @Override
    public BaseDto<ProductItemDto> addProductItem(ProductItemDto productItemDto, MultipartFile file) throws IOException {
        BaseDto<ProductItemDto> response= new BaseDto<ProductItemDto>();
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
        ProductItemDto dataResponse = new ProductItemDto();
        Product product=productRepository.findByProductIdNoDelete(productItemDto.getProductId());
        if(product==null){
            response.setMessage("Product not found");
            response.setSuccess(false);
            return response;
        }
        product.setPrice(productItemDto.getPrice());
        if(productItemDto.getIdColor() == 0L){

            response.setData(dataResponse);
            response.setMessage("Hiện tại chưa có màu sắc để thêm vào sản phẩm");
            response.setSuccess(false);
            return response;
        }
        List<ProductItem>productItemList=product.getProductItems();
        for(ProductItem productItem:productItemList){
            if(productItem.getIdColor()==productItemDto.getIdColor() && productItem.isDeleted() == false){
                response.setMessage("Product item already exists");
                response.setSuccess(false);
                return response;
            }
        }
        ProductItem productItem= new ProductItem();
        productItem.setProduct(product);
        productItem.setPrice(productItemDto.getPrice());
        productItem.setQyt_stock(0);
        productItem.setProductItemImage(fileName);
        productItem.setIdColor(productItemDto.getIdColor());
        ProductItem save= productItemRepository.save(productItem);

        dataResponse.setProductId(productItem.getId());
        dataResponse.setPrice(productItem.getPrice());
        dataResponse.setQyt_stock(productItem.getQyt_stock());
        dataResponse.setImage(fileName);
        response.setSuccess(true);
        response.setData(dataResponse);
        response.setMessage("Product Item create successfully");
        return response;
    }

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
            Product product=productItem.getProduct();
            if(productItem==null){
                return null;
            }
            ProductDto dto= new ProductDto();
            dto.setId(productItem.getId());
            dto.setDescription(product.getDescription());
            dto.setProductName(product.getProductName());
            dto.setQuantity(productItem.getQyt_stock());
            dto.setPrice(productItem.getPrice());
            dto.setImage(productItem.getProductItemImage());
            return dto;


    }

    @Override
    public ProductDto getProductItemByIsColor(Long productItemId, Long idColor) {
        return null;
    }

    @Override
    public void deleteProductItemById(Long productItemId) {
        ProductItem deleteProductItem= productItemRepository.findById(productItemId).orElseThrow();
        deleteProductItem.setDeleted(true);
        productItemRepository.save(deleteProductItem);
    }

    @Override
    public BaseDto<List<ProductItemDto>>  getAllProductItemByProduct(Long productId) {
        BaseDto<List<ProductItemDto>> response= new BaseDto<List<ProductItemDto>> ();
        Product product=productRepository.findByProductIdNoDelete(productId);
        if(product==null){
            response.setMessage("Product not found");
            response.setSuccess(false);
            return response;
        }
        List<ProductItem> productItems=productItemRepository.getAllProductItemByProductNoDelete(product);
        if(productItems==null){
            response.setMessage("Product Item empty in product");
            response.setSuccess(true);
            return response;
        }
        List<ProductItemDto>productItemDtos=new ArrayList<>();
        for (ProductItem productItem:productItems) {
                ProductItemDto productItemDto= new ProductItemDto();
                productItemDto.setId(productItem.getId());
                productItemDto.setIdColor(productItem.getIdColor());
                productItemDto.setPrice(productItem.getPrice());
                productItemDto.setQyt_stock(productItem.getQyt_stock());
                productItemDto.setImage(productItem.getProductItemImage());
                productItemDto.setProductId(productItem.getProduct().getId());
                productItemDtos.add(productItemDto);
        }
        response.setSuccess(true);
        response.setData(productItemDtos);
        response.setMessage("Product Item get list successfully");
        return response;
    }

    @Override
    public List<ProductItemDto> getAllProductItem() {
        List<ProductItem>productItems= productItemRepository.findByProductAllNoDelete();
        List<ProductItemDto>productItemDtos= new LinkedList<>();
        if(productItems==null){
            return null;
        }
        for (ProductItem item:productItems) {
                ProductItemDto dto= new ProductItemDto();
                dto.setProductId(item.getId());
                dto.setProductId(item.getProduct().getId());
                dto.setIdColor(item.getIdColor());
                dto.setImage(item.getProductItemImage());
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
    public ProductItem getProductItemByProductAndVarationOption(List<ColorSizeDto> productVariationDtos) {
        ProductItem productItem = new ProductItem();
        return productItem;
    }


}
