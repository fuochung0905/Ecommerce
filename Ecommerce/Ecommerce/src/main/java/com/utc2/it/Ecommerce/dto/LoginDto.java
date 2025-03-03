package com.utc2.it.Ecommerce.dto;

import com.utc2.it.Ecommerce.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private Long userId;
    private Role role;
    private String token;
}
