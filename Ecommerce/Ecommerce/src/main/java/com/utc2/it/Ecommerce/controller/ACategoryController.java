package com.utc2.it.Ecommerce.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.dto.CategoryDto;
import com.utc2.it.Ecommerce.dto.DeleteResponse;
import com.utc2.it.Ecommerce.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class ACategoryController {
    private final CategoryService categoryService;
    @PostMapping("/createNewCategory")
    public ResponseEntity<?>createCategory(@Valid @RequestBody CategoryDto dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid request");
        }
        CategoryDto categoryDto= categoryService.createCategory(dto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }
    @PutMapping("updateCategory/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(@RequestBody CategoryDto dto,@PathVariable Long categoryId){
        CategoryDto categoryDto= categoryService.updateCategory(categoryId,dto);
        return  new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>getCategoryById(@PathVariable Long categoryId){
        CategoryDto categoryDto= categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }
    @DeleteMapping("deleteProduct/{categoryId}")
    public ResponseEntity<DeleteResponse>deleteCategoryById(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new DeleteResponse(" category is delete successfully "),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>>getAllCategories(){
        List<CategoryDto> categoryDtos= categoryService.getAllCategory();
        return new ResponseEntity<>(categoryDtos,HttpStatus.OK);
    }
}
