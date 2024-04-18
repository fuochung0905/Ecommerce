package com.utc2.it.Ecommerce.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class AImageController {

    @Value("${images.directory}")
    private String imagesDirectory;

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get(imagesDirectory).resolve(imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());
        return ResponseEntity.ok()
                .contentLength(imageResource.contentLength())
                .contentType(MediaType.IMAGE_JPEG) // Đặt kiểu Content-Type tùy thuộc vào loại ảnh bạn lưu trữ
                .body(imageResource);
    }
}