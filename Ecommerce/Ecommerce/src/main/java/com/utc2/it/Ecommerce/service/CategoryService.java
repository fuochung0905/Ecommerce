package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto dto);
    CategoryDto updateCategory(Long categoryId,CategoryDto dto);
    CategoryDto getCategoryById(Long categoryId);
    void deleteCategory(Long categoryId);
    List<CategoryDto>getAllCategory();
}
