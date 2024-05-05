package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.UserDto;
import com.utc2.it.Ecommerce.entity.User;
import com.utc2.it.Ecommerce.repository.UserRepository;
import com.utc2.it.Ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setPhoneNumber(user.getPhoneNumber());
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public UserDto getUserById(Long userId) {
      User user = userRepository.findById(userId).orElse(null);
      UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        return userDto;
    }

    @Override
    public void deleteUser(Long userId) {
    User user = userRepository.findById(userId).orElse(null);
    userRepository.delete(user);
    }

    @Override
    public Double getTotalPriceOrder(Long userId) {
        return 0.0;
    }

}
