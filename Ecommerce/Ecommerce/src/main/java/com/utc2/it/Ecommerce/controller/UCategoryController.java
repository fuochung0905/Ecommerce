package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.CategoryDto;
import com.utc2.it.Ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/guest/category")
public class UCategoryController {
    private final CategoryService categoryService;
    @GetMapping("/")
    public ResponseEntity<?> getCategory() {
        List<CategoryDto>categoryDtos=categoryService.getAllCategory();
        if(categoryDtos==null || categoryDtos.size()==0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(categoryDtos);
    }
}
