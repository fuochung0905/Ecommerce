package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.DeleteResponse;
import com.utc2.it.Ecommerce.dto.UserDto;
import com.utc2.it.Ecommerce.service.AuthService;
import com.utc2.it.Ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/user")
@RequiredArgsConstructor
public class AUserController {
    private final UserService userService;
    private final AuthService authService;
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        UserDto user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<DeleteResponse> deleteUserById(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new DeleteResponse("User deleted successfully"));
    }
    @GetMapping("/current")
    public ResponseEntity<UserDto>getCurrentLogin(){
        UserDto userDto=authService.getCurrentUser();
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
