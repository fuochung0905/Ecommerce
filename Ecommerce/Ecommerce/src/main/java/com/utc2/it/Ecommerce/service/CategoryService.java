package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.Base.BaseDto;
import com.utc2.it.Ecommerce.Base.BaseEntity;
import com.utc2.it.Ecommerce.dto.CategoryDto;
import com.utc2.it.Ecommerce.dto.DeleteResponse;

import java.util.List;

public interface CategoryService {
    BaseDto<CategoryDto> createCategory(CategoryDto dto);
    BaseDto<CategoryDto> updateCategory(Long categoryId,CategoryDto dto);
    BaseDto<CategoryDto> getCategoryById(Long categoryId);
    BaseDto<DeleteResponse> deleteCategory(Long categoryId);
    BaseDto<List<CategoryDto>> getAllCategory();
}
