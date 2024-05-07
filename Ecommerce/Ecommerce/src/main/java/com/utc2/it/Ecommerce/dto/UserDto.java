package com.utc2.it.Ecommerce.dto;

import lombok.Data;

@Data
public class UserDto {
    private  Long Id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;
    private String image;

}