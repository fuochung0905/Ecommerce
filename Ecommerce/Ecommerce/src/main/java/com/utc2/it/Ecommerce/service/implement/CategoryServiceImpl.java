package com.utc2.it.Ecommerce.service.implement;

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
    public CategoryDto createCategory(CategoryDto dto) {
        Category find=categoryRepository.findByCategoryName(dto.getName());
        if(find==null){
            Category category = new Category();
            category.setCategoryName(dto.getName());
            category.setShow(true);
            categoryRepository.save(category);
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getCategoryName());
            return categoryDto;
        }
        else {
            return null;
        }

    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto dto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category", "categoryId", categoryId));
        category.setCategoryName(dto.getName());
        categoryRepository.save(category);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getCategoryName());
        return categoryDto;
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category", "categoryId", categoryId));
        if(category.isShow()){
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getCategoryName());
            return categoryDto;
        }
       return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("category", "categoryId", categoryId));
       category.setShow(false);
       categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
            List<CategoryDto> categoryDtos = new LinkedList<>();
            for (Category category : categories) {
                if(category.isShow()){
                    CategoryDto dto = new CategoryDto();
                    dto.setId(category.getId());
                    dto.setName(category.getCategoryName());
                    categoryDtos.add(dto);
                }

            }
            return categoryDtos;
    }
}

