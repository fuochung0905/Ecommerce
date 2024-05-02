package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.dto.SignUpRequest;
import com.utc2.it.Ecommerce.entity.User;
import com.utc2.it.Ecommerce.repository.UserRepository;
import com.utc2.it.Ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/information")

public class UserController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private static final String UPLOAD_DIR = "src/main/resources/images";
    private User getUser(String username){
        User user=userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user;
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Trả về tên người dùng hiện đang đăng nhập
    }
    @PostMapping("/")
    public ResponseEntity<?>updateUser(@RequestBody SignUpRequest signUpRequest) throws IOException {
        Long result= authService.updateUser(signUpRequest);
        return ResponseEntity.ok().body(result);
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateAvatar(@RequestParam("file") MultipartFile file) throws IOException {

        try {
            String username = getCurrentUsername();
            User user = getUser(username);
            String fileName = saveImageToDirectory(file);
            authService.saveUserImage(user.getId(), fileName);
            return new ResponseEntity<>("Update user successfully", HttpStatus.CREATED);
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload image: " + ex.getMessage());
        }
    }

    private String saveImageToDirectory(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }
    }
}
