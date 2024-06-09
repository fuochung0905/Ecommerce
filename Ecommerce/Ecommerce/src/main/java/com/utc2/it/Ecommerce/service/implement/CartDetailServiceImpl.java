package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.CartDetailDto;
import com.utc2.it.Ecommerce.entity.CartDetail;
import com.utc2.it.Ecommerce.entity.ShoppingCart;
import com.utc2.it.Ecommerce.entity.User;
import com.utc2.it.Ecommerce.repository.CartDetailRepository;
import com.utc2.it.Ecommerce.repository.ShoppingCartRepository;
import com.utc2.it.Ecommerce.repository.UserRepository;
import com.utc2.it.Ecommerce.service.CartDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {
    private final CartDetailRepository cartDetailRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    @Override
    public CartDetailDto getCartDetailById(Long cartDetailById) {
        CartDetail cartDetail=cartDetailRepository.findById(cartDetailById).orElseThrow();
        CartDetailDto cartDetailDto= new CartDetailDto();
        cartDetailDto.setId(cartDetail.getId());
        cartDetailDto.setQuantity(cartDetail.getQuantity());
        cartDetailDto.setPrice(cartDetail.getPrice());
        cartDetailDto.setProductName(cartDetail.getProductItem().getProduct().getProductName());
        cartDetailDto.setSize(cartDetail.getSize());
        cartDetailDto.setColor(cartDetail.getColor());
        return cartDetailDto;
    }

    @Override
    public List<CartDetailDto> getAllCartDetail() {
        String username=getCurrentUsername();
        User user=getUser(username);
        ShoppingCart shoppingCart=getCart(user);
        List<CartDetailDto>cartDetailDtos= new ArrayList<>();
        List<CartDetail>cartDetails=shoppingCart.getCartDetails();
        for(CartDetail cartDetail:cartDetails){
            CartDetailDto cartDetailDto= new CartDetailDto();
            cartDetailDto.setId(cartDetail.getId());
            cartDetailDto.setQuantity(cartDetail.getQuantity());
            cartDetailDto.setPrice(cartDetail.getPrice());
            cartDetailDto.setProductName(cartDetail.getProductItem().getProduct().getProductName());
            cartDetailDto.setSize(cartDetail.getSize());
            cartDetailDto.setColor(cartDetail.getColor());
            cartDetailDtos.add(cartDetailDto);
        }
        return cartDetailDtos;
    }
    private ShoppingCart getCart(User user){
        ShoppingCart shoppingCart=shoppingCartRepository.findShoppingCartByUser(user);
        return shoppingCart;
    }
    private User getUser(String username){
        User user=userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user;
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Trả về tên người dùng hiện đang đăng nhập
    }
}
