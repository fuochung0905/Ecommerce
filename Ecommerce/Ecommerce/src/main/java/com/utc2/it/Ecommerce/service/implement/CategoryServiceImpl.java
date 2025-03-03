package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.Base.BaseDto;
import com.utc2.it.Ecommerce.dto.DeleteResponse;
import com.utc2.it.Ecommerce.entity.Product;
import com.utc2.it.Ecommerce.repository.ProductRepository;
import com.utc2.it.Ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.dto.CategoryDto;
import com.utc2.it.Ecommerce.entity.Category;
import com.utc2.it.Ecommerce.exception.ResourceNotFoundException;
import com.utc2.it.Ecommerce.repository.CategoryRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public BaseDto<CategoryDto> createCategory(CategoryDto dto) {
        BaseDto<CategoryDto> response = new BaseDto<CategoryDto>();
        Category find=categoryRepository.findByCategoryNameNoDelete(dto.getName());
        if(find !=null){
            response.setSuccess(false);
            response.setMessage("Category already exists");
            return response;
        }
        Category category = new Category();
        category.setCategoryName(dto.getName());
        categoryRepository.save(category);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getCategoryName());
        response.setSuccess(true);
        response.setData(categoryDto);
        response.setMessage("Category created successfully");
        return response;

    }

    @Override
    public BaseDto<CategoryDto> updateCategory(Long categoryId, CategoryDto dto) {
        BaseDto<CategoryDto> response = new BaseDto<CategoryDto>();
        Category category = categoryRepository.findByCategoryIdNoDelete(categoryId);
        if(category == null){
            response.setSuccess(false);
            response.setMessage("Category not found");
            return response;
        }
        category.setCategoryName(dto.getName());
        categoryRepository.save(category);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getCategoryName());
        response.setData(categoryDto);
        response.setSuccess(true);
        response.setMessage("Category updated successfully");
        return response;
    }

    @Override
    public BaseDto<CategoryDto> getCategoryById(Long categoryId) {
        BaseDto<CategoryDto> response = new BaseDto<CategoryDto>();
        Category category = categoryRepository.findByCategoryIdNoDelete(categoryId);
        if(category == null){
            response.setSuccess(false);
            response.setMessage("Category not found");
            return response;
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getCategoryName());
        response.setSuccess(true);
        response.setData(categoryDto);
        response.setMessage("get category successfully");
        return response;
    }

    @Override
    public BaseDto<DeleteResponse> deleteCategory(Long categoryId) {
        BaseDto<DeleteResponse> response = new BaseDto<DeleteResponse>();
        Category category = categoryRepository.findByCategoryIdNoDelete(categoryId);
        if(category == null){
            response.setSuccess(false);
            response.setMessage("Category not found");
            return response;
        }
       category.setDeleted(true);
       categoryRepository.save(category);
       response.setSuccess(true);
       response.setMessage("Category deleted successfully");
       DeleteResponse deleteResponse = new DeleteResponse();
       deleteResponse.setResponse("Delete category" + category.getCategoryName());
       return response;
    }

    @Override
    public BaseDto<List<CategoryDto>> getAllCategory() {
        BaseDto<List<CategoryDto>> response = new BaseDto<List<CategoryDto>>();
        List<Category> categories = categoryRepository.findByCategoryAllNoDelete();
        if(categories == null || categories.isEmpty()){
            response.setSuccess(true);
            response.setMessage("category is empty");
            return response;
        }
        List<CategoryDto> categoryDtos = new LinkedList<>();
        for (Category category : categories) {
            CategoryDto dto = new CategoryDto();
            dto.setId(category.getId());
            dto.setName(category.getCategoryName());
            categoryDtos.add(dto);
        }
        response.setSuccess(true);
        response.setData(categoryDtos);
        response.setMessage("get all category successfully");
        return response;
    }
}

