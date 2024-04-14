package com.utc2.it.Ecommerce.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.dto.AddressDto;
import com.utc2.it.Ecommerce.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/user/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    @PostMapping("/createNewAddress")
    public ResponseEntity<AddressDto>createAddress(@RequestBody AddressDto addressDto){
        AddressDto dto=addressService.createAddress(addressDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<AddressDto>>getAllAddressByUser(){
        List<AddressDto>addressDtos=addressService.getAllAddress();
        return new ResponseEntity<>(addressDtos,HttpStatus.OK);
    }
    @PostMapping("/updateIsDefine")
    public ResponseEntity<AddressDto>updateAddress(@RequestBody AddressDto dto){
    AddressDto addressDto=addressService.updateIsDefine(dto);
    return new ResponseEntity<>(addressDto,HttpStatus.OK);
    }
}
