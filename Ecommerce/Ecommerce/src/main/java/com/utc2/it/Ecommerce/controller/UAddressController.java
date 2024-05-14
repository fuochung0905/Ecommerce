package com.utc2.it.Ecommerce.controller;

import jakarta.validation.Valid;
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
public class UAddressController {
    private final AddressService addressService;
    @PostMapping("/createNewAddress")
    public ResponseEntity<AddressDto>createAddress(@Valid @RequestBody AddressDto addressDto){
        AddressDto dto=addressService.createAddress(addressDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/")
    public ResponseEntity<List<AddressDto>>getAllAddressByUser(){
        List<AddressDto>addressDtos=addressService.getAllAddress();
        if(addressDtos==null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(addressDtos,HttpStatus.OK);
    }
    @PostMapping("/updateIsDefine")
    public ResponseEntity<AddressDto>updateAddress(@RequestBody AddressDto dto){
    AddressDto addressDto=addressService.updateIsDefine(dto);
    return new ResponseEntity<>(addressDto,HttpStatus.OK);
    }
}
