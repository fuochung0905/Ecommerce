package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.CartService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.utc2.it.Ecommerce.dto.CartDto;
import com.utc2.it.Ecommerce.dto.UserCartDto;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductItemRepository productItemRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private User getUser(String username){
        User user=userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user;
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Trả về tên người dùng hiện đang đăng nhập
    }

    public Integer AddItem(Long productId, CartDto cartDto) throws Exception {
        if (cartDto.getColor() == null) {
            cartDto.setColor("");
        }
        if (cartDto.getSize() == null) {
            cartDto.setSize("");
        }
        ShoppingCart newShoppingCart = new ShoppingCart();
        String currentUsername = getCurrentUsername();
        User user = getUser(currentUsername);
        if (user == null) {
            return  0;
        }
        ShoppingCart checkShoppingCart = getCart(user);
        if (checkShoppingCart == null) {
            newShoppingCart.setUser(user);
            shoppingCartRepository.save(newShoppingCart);
        }
        else {
            ProductItem product = productItemRepository.findById(productId).orElseThrow();
            CartDetail cartDetail = cartDetailRepository.findCartDetailByShopping_cartAndProduct(checkShoppingCart, product);

            if(cartDetail==null) {
                CartDetail newCart= new CartDetail();
                newCart.setQuantity(1);
                newCart.setProductItem(product);
                newCart.setSize(cartDto.getSize());
                newCart.setColor(cartDto.getColor());
                double totalPrice = product.getPrice() * 1;
                newCart.setPrice(totalPrice);
                newCart.setShopping_cart(checkShoppingCart);
                cartDetailRepository.save(newCart);
                Integer carCount= cartDetailRepository.GetCartItemCount(user);
                return carCount;
            }
            else {
                cartDetail.setQuantity(cartDetail.getQuantity()+1);
                cartDetailRepository.save(cartDetail);
                Integer carCount= cartDetailRepository.GetCartItemCount(user);
                return carCount;
            }
        }

        return 0;
    }
    @Transactional
    public Integer RemoveCartItem(Long productId) throws Exception {
        String currentUsername = getCurrentUsername();
        User user = getUser(currentUsername);
        if (user == null) {
            return 0;
        }
        ShoppingCart checkShoppingCart = getCart(user);
        if (checkShoppingCart == null) {
            return 0;
        }

        ProductItem productItem=productItemRepository.findById(productId).orElseThrow();
        CartDetail cartDetail = cartDetailRepository.findCartDetailByShopping_cartAndProduct(checkShoppingCart, productItem);
        if(cartDetail == null){
            return 0;
        }
        else if (cartDetail.getQuantity()==1){
            cartDetailRepository.delete(cartDetail);
        }
        else {
            int quantity=cartDetail.getQuantity();
            cartDetail.setQuantity(quantity-1);
            cartDetailRepository.save(cartDetail);
        }
        Integer cartCount= cartDetailRepository.GetCartItemCount(user);
        return cartCount;
    }

    @Override
    public List<UserCartDto> getUserCart() throws Exception {
        String username=getCurrentUsername();
        User user=getUser(username);
        if(user==null){
            throw new Exception("không tìm thấy người dùng");
        }
        ShoppingCart shoppingCart=getUserShoppingCart(username);
        if(shoppingCart!=null){
            List<UserCartDto> userCartDtoS= new LinkedList<>();
            for (CartDetail cart: shoppingCart.getCartDetails()) {
                UserCartDto userCartDto= new UserCartDto();
                userCartDto.setId(cart.getId());
                ProductItem productItem=productItemRepository.findById(cart.getProductItem().getId()).orElseThrow();
                Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                userCartDto.setProductName(product.getProductName());
                userCartDto.setSize(cart.getSize());
                userCartDto.setColor(cart.getColor());
                userCartDto.setQuantity(cart.getQuantity());
                userCartDto.setPrice(cart.getPrice());
                userCartDto.setImage(productItem.getProductItemImage());
                userCartDtoS.add(userCartDto);
            }
            return userCartDtoS;
        }
        throw new Exception("không tìm thấy giỏ hàng");
    }
    @Override
    public Integer getCartCount() {
        String username=getCurrentUsername();
        User user=getUser(username);
        Integer cartItemCount=cartDetailRepository.GetCartItemCount(user);
        return cartItemCount;
    }

    public ShoppingCart getUserShoppingCart(String userName) throws Exception {
        if (userName == null) {
            throw new Exception("Invalid user");
        }
        ShoppingCart shoppingCart = entityManager.createQuery(
                        "SELECT sc FROM ShoppingCart sc " +
                                "JOIN FETCH sc.cartDetails cd " +
                                "JOIN FETCH cd.productItem p " +
                                "WHERE sc.user.email = :userName", ShoppingCart.class)
                .setParameter("userName", userName)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        if (shoppingCart == null) {
            throw new Exception("Shopping cart not found");
        }
        return shoppingCart;
    }
    private ShoppingCart getCart(User user){
        ShoppingCart shoppingCart=shoppingCartRepository.findShoppingCartByUser(user);
        return shoppingCart;
    }

}
