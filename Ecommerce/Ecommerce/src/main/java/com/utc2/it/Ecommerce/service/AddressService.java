package com.utc2.it.Ecommerce.service;

import com.utc2.it.Ecommerce.dto.AddressDto;

import java.util.List;

public interface AddressService {
    AddressDto createAddress(AddressDto addressDto);
    List<AddressDto>getAllAddress();
    AddressDto updateIsDefine(AddressDto addressDto);

}
