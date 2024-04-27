package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.entity.Address;
import com.utc2.it.Ecommerce.dto.AddressDto;
import com.utc2.it.Ecommerce.entity.User;
import com.utc2.it.Ecommerce.repository.AddressRepository;
import com.utc2.it.Ecommerce.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private User getUser(String username){
        User user=userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user;
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Trả về tên người dùng hiện đang đăng nhập
    }
    @Override
    public AddressDto createAddress(AddressDto addressDto) {
        String username=getCurrentUsername();
        User user=getUser(username);
        List<Address>addresses=addressRepository.getListAddressByUser(user);
        for (Address tamp:addresses) {
            tamp.setDefine(false);
            addressRepository.save(tamp);
        }
        Address address= new Address();
        address.setState(addressDto.getState());
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setDefine(true);
        address.setUser(user);
        Address saveAddress=addressRepository.save(address);
        AddressDto dto= new AddressDto();
        dto.setCity(saveAddress.getCity());
        dto.setStreet(saveAddress.getStreet());
        dto.setState(saveAddress.getState());
        dto.setCountry(saveAddress.getCountry());
        dto.setUserId(saveAddress.getId());
        return dto;
    }


    @Override
    public List<AddressDto> getAllAddress() {
        String username=getCurrentUsername();
        User user=getUser(username);
        List<AddressDto>addressDtos= new ArrayList<>();
        List<Address>addresses=addressRepository.getListAddressByUser(user);
        if(addresses.isEmpty())
            return null;
        for (Address address:addresses) {
                AddressDto dto=new AddressDto();
                dto.setId(address.getId());
            dto.setCity(address.getCity());
            dto.setStreet(address.getStreet());
            dto.setState(address.getState());
            dto.setCountry(address.getCountry());
            dto.setUserId(address.getId());
            addressDtos.add(dto);
        }
        return addressDtos;
    }

    @Override
    public AddressDto updateIsDefine(AddressDto addressDto) {
        List<Address>addressList=addressRepository.findAll();
        List<Address> listTamp=new ArrayList<>();
        for (Address add:addressList) {
            add.setDefine(false);
            listTamp.add(add);
        }
        addressRepository.saveAll(listTamp);
        Address findaddress=addressRepository.findAddressById(addressDto.getId());
            findaddress.setDefine(true);
           Address update= addressRepository.save(findaddress);
            AddressDto dto= new AddressDto();
            dto.setStreet(update.getStreet());

        return dto;
    }
}
