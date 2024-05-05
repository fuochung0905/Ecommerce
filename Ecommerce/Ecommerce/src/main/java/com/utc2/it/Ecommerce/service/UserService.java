package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto>getAllUser();
    UserDto getUserById(Long userId);
    void deleteUser(Long userId);
    Double getTotalPriceOrder(Long userId);
}
