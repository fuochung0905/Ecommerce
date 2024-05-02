package com.utc2.it.Ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.utc2.it.Ecommerce.dto.UserDto;
import com.utc2.it.Ecommerce.service.AuthService;

@RestController
@RequestMapping("/api/user/currentUser")
@RequiredArgsConstructor
public class CurrentUserController {
    private final AuthService authService;
    @GetMapping("/")
    public ResponseEntity<UserDto>getCurrentUser(){
        UserDto currentUser=authService.getCurrentUser();

        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }
}
