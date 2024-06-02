package com.utc2.it.Ecommerce.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utc2.it.Ecommerce.dto.ColorSizeDto;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.RedisShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.exception.NotFoundException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RedisShoppingCartServiceImpl implements RedisShoppingCartService {
    private final UserRepository userRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductItemVariationOptionRepository productItemVariationOptionRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductItemRepository productItemRepository;
    private final CartDetailRepository cartDetailRepository;
    private ShoppingCart getCart(User user){
        return shoppingCartRepository.findShoppingCartByUser(user);
    }
    @Override
    public void addToCart(ColorSizeDto productVariationDtos) {
        if(productVariationDtos.getVariationOptionId()==0){
            boolean check=false;
            ProductItem productItem=productItemRepository.findById(productVariationDtos.getIdColor()).orElseThrow();
            VariationOption findByColor=variationOptionRepository.findById(productItem.getIdColor()).orElseThrow();
            ShoppingCart newShoppingCart = new ShoppingCart();
            String currentUsername = getCurrentUsername();
            User user = getUser(currentUsername);
            if (user == null) {
                throw new NotFoundException("User not found");
            }
            ProductItemVariationOption productItemVariationOption=productItemVariationOptionRepository.findProductItemVariationOptionByProductItemAndVariationOption(productItem,findByColor);
            if(productItemVariationOption.getQuantity()< productVariationDtos.getQuantity()){
                throw new NotFoundException("Sản pẩm này không đủ số lượng bạn mua");
            }
            ShoppingCart checkShoppingCart = getCart(user);
            if (checkShoppingCart == null) {
                newShoppingCart.setUser(user);
                shoppingCartRepository.save(newShoppingCart);
            }
            else {
                List<CartDetail>  cartDetails = cartDetailRepository.findCartDetailByShopping_cartAndProduct(checkShoppingCart, productItem);
                if (cartDetails == null) {
                    CartDetail newCart = new CartDetail();
                    newCart.setQuantity(productVariationDtos.getQuantity());
                    newCart.setIdColor(findByColor.getId());
                    newCart.setProductItem(productItem);
                    newCart.setColor(findByColor.getValue());
                    double totalPrice = productItem.getPrice() * 1;
                    newCart.setPrice(totalPrice);
                    newCart.setShopping_cart(checkShoppingCart);
                    CartDetail save = cartDetailRepository.save(newCart);
                }
                else {
                    for(CartDetail cartDetail:cartDetails)
                    {
                        if(Objects.equals(cartDetail.getColor(), findByColor.getValue()) )
                        {
                            check=true;
                            if (check) {
                                cartDetail.setQuantity(cartDetail.getQuantity() + productVariationDtos.getQuantity());
                                CartDetail save = cartDetailRepository.save(cartDetail);
                            }
                        }
                    }
                    if(!check){
                        CartDetail newCart = new CartDetail();
                        newCart.setQuantity(productVariationDtos.getQuantity());
                        newCart.setIdColor(findByColor.getId());

                        newCart.setProductItem(productItem);

                        newCart.setColor(findByColor.getValue());
                        double totalPrice = productItem.getPrice() * 1;
                        newCart.setPrice(totalPrice);
                        newCart.setShopping_cart(checkShoppingCart);
                        CartDetail save = cartDetailRepository.save(newCart);
                    }
                }
            }
        }
        boolean check=false;
       ProductItem productItem=productItemRepository.findById(productVariationDtos.getIdColor()).orElseThrow();
        VariationOption findBySize=variationOptionRepository.findById(productVariationDtos.getVariationOptionId()).orElseThrow();
        VariationOption findByColor=variationOptionRepository.findById(productItem.getIdColor()).orElseThrow();
        ShoppingCart newShoppingCart = new ShoppingCart();
        String currentUsername = getCurrentUsername();
        User user = getUser(currentUsername);
        if (user == null) {
           throw new NotFoundException("User not found");
        }

        ShoppingCart checkShoppingCart = getCart(user);
        if (checkShoppingCart == null) {
            newShoppingCart.setUser(user);
            shoppingCartRepository.save(newShoppingCart);
        }
        else {
           List<CartDetail>  cartDetails = cartDetailRepository.findCartDetailByShopping_cartAndProduct(checkShoppingCart, productItem);
              if (cartDetails == null) {
                  CartDetail newCart = new CartDetail();
                  newCart.setQuantity(productVariationDtos.getQuantity());
                  newCart.setIdColor(findByColor.getId());
                  newCart.setIdSize(findBySize.getId());
                  newCart.setProductItem(productItem);
                  newCart.setSize(findBySize.getValue());
                  newCart.setColor(findByColor.getValue());
                  double totalPrice = productItem.getPrice() * 1;
                  newCart.setPrice(totalPrice);
                  newCart.setShopping_cart(checkShoppingCart);
                  CartDetail save = cartDetailRepository.save(newCart);
              }
              else {
                  for(CartDetail cartDetail:cartDetails)
                  {
                      if(Objects.equals(cartDetail.getColor(), findByColor.getValue()) && Objects.equals(cartDetail.getSize(), findBySize.getValue()))
                      {
                          check=true;
                          if (check) {
                              cartDetail.setQuantity(cartDetail.getQuantity() + productVariationDtos.getQuantity());
                              CartDetail save = cartDetailRepository.save(cartDetail);
                          }
                      }
                  }
                  if(!check){
                          CartDetail newCart = new CartDetail();
                          newCart.setQuantity(productVariationDtos.getQuantity());
                          newCart.setIdColor(findByColor.getId());
                          newCart.setIdSize(findBySize.getId());
                          newCart.setProductItem(productItem);
                          newCart.setSize(findBySize.getValue());
                          newCart.setColor(findByColor.getValue());
                          double totalPrice = productItem.getPrice() * 1;
                          newCart.setPrice(totalPrice);
                          newCart.setShopping_cart(checkShoppingCart);
                          CartDetail save = cartDetailRepository.save(newCart);
                  }
              }
        }
    }

    @Override
    public void removeCart(ColorSizeDto productVariationDtos) {



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
