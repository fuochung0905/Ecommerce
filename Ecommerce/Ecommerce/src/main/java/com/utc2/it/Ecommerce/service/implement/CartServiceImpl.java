package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.ColorSizeDto;
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
   private final VariationOptionRepository variationOptionRepository;
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
                userCartDto.setIdColor(cart.getIdColor());
                userCartDto.setIdSize(cart.getIdSize());
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
       ShoppingCart shoppingCart=shoppingCartRepository.findShoppingCartByUserWithProduct(userName);
        if (shoppingCart == null) {
            return null;
        }
        return shoppingCart;
    }
    private ShoppingCart getCart(User user){
        ShoppingCart shoppingCart=shoppingCartRepository.findShoppingCartByUser(user);
        return shoppingCart;
    }

}
