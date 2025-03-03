package com.utc2.it.Ecommerce.controller;


import com.utc2.it.Ecommerce.Base.BaseDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.dto.ProductDto;
import com.utc2.it.Ecommerce.dto.SignInRequest;
import com.utc2.it.Ecommerce.dto.SignUpRequest;
import com.utc2.it.Ecommerce.dto.UserDto;
import com.utc2.it.Ecommerce.service.AuthService;
import com.utc2.it.Ecommerce.service.ProductService;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(value = "http://localhost:4200",allowedHeaders = "*",allowCredentials = "false",maxAge = 3600)

public class JwtAuthentication {
    private final AuthService authService;
    private final ProductService productService;
    @PostMapping("/register")
    public ResponseEntity<UserDto>register( @Valid @RequestBody SignUpRequest signUpRequest){
        UserDto userDto= authService.CreateUser(signUpRequest);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
  @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/login")
    public void login(@RequestBody SignInRequest request, HttpServletResponse httpServletResponse) throws IOException, JSONException {
        authService.signin(request,httpServletResponse);
    }
    @GetMapping("/")
    public ResponseEntity<?> getAllProduct(){
        BaseDto<List<ProductDto>> productDtos=productService.getAllProduct();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?>getProductById(@PathVariable Long productId){
        BaseDto<ProductDto> productDto= productService.getProductById(productId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

}
