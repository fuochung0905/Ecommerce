package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.ReviewDto;
import com.utc2.it.Ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/review")

public class UReviewController {
    private final ReviewService reviewService;
    private static final String UPLOAD_DIR = "src/main/resources/images";
    @PostMapping("/createNewReview")
    public ResponseEntity<String> createNewReview( ReviewDto dto,@RequestParam("file")MultipartFile file) {
        Long reviewDto= reviewService.createReview(dto);
        try {
            String fileName=saveImageToDirectory(file);
            reviewService.saveReviewImage(reviewDto,fileName);
            return ResponseEntity.ok("Add successful review");
        }catch (IOException ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload image: " + ex.getMessage());
        }

    }
    private String saveImageToDirectory(MultipartFile file)throws IOException {
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream=file.getInputStream()) {
            Path filePath=uploadPath.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }
    }
    @GetMapping("product/{productId}")
    public ResponseEntity<List<ReviewDto>> getReviewById(@PathVariable Long productId) {
        List<ReviewDto>getAllReviewByProduct=reviewService.getAllReviewsByProductId(productId);
        return new ResponseEntity<>(getAllReviewByProduct, HttpStatus.OK);
    }
}
