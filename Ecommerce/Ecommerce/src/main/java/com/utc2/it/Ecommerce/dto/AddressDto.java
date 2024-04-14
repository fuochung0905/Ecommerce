package com.utc2.it.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long Id;
    private String street;
    private String city;
    private String state;
    private String country;
    private boolean isDefine;
    private Long  userId;
}
