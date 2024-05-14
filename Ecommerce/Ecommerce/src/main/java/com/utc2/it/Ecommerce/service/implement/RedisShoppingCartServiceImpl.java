package com.utc2.it.Ecommerce.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utc2.it.Ecommerce.dto.ColorSizeDto;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.RedisShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.utc2.it.Ecommerce.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RedisShoppingCartServiceImpl implements RedisShoppingCartService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductItemRepository productItemRepository;
    private static final String SHOPPING_CART_KEY_PREFIX = "shopping_cart:";
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
        boolean check=false;
        ProductItem productItem=productItemRepository.findById(productVariationDtos.getIdColor()).orElseThrow();
        VariationOption findBySize=variationOptionRepository.findById(productItem.getIdColor()).orElseThrow();
        VariationOption findByColor=variationOptionRepository.findById(productVariationDtos.getIdColor()).orElseThrow();
        ShoppingCart newShoppingCart = new ShoppingCart();
        String currentUsername = getCurrentUsername();
        User user = getUser(currentUsername);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        if(productItem==null){
            throw  new NotFoundException("Product item not found");
        }
        ShoppingCart checkShoppingCart = getCart(user);
        if (checkShoppingCart == null) {
            newShoppingCart.setUser(user);
            shoppingCartRepository.save(newShoppingCart);
        }
        else {
            List<CartDetail>  cartDetails = cartDetailRepository.findCartDetailByShopping_cartAndProduct(checkShoppingCart, productItem);
                for(CartDetail cartDetail:cartDetails)
                {
                    if(Objects.equals(cartDetail.getColor(), findByColor.getValue()) && Objects.equals(cartDetail.getSize(), findBySize.getValue()))
                    {
                        check=true;
                        if (check) {
                            if(cartDetail.getQuantity()>1){
                                cartDetail.setQuantity(cartDetail.getQuantity() - 1);
                                cartDetailRepository.save(cartDetail);
                            }
                            else {
                                cartDetailRepository.delete(cartDetail);
                            }
                            // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
                        }
                    }
                }
        }
    }
    @Override
    public Long getCartCount() {
        String username=getCurrentUsername();
        User user=getUser(username);
        String key =SHOPPING_CART_KEY_PREFIX+user.getId().toString();
        Long cartCount=redisTemplate.opsForHash().size(key);
        return cartCount;
    }

    @Override
    public List<ShoppingCartItem> getUserCart() throws Exception {
        String username = getCurrentUsername();
        User user = getUser(username);
        if (user == null) {
            throw new Exception("Không tìm thấy người dùng");
        }
        String key = SHOPPING_CART_KEY_PREFIX + user.getId().toString();
        // Lấy tất cả các giá trị từ hash
        Map<Object, Object> shoppingCartItemsMap = redisTemplate.opsForHash().entries(key);
        List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
        // Duyệt qua từng phần tử trong map và chuyển đổi từ JSON thành đối tượng
        for (Map.Entry<Object, Object> entry : shoppingCartItemsMap.entrySet()) {
            String jsonString = (String) entry.getValue(); // Chuyển đổi giá trị sang String
            ShoppingCartItem item = objectMapper.readValue(jsonString, ShoppingCartItem.class);
            shoppingCartItems.add(item);
        }
        return shoppingCartItems;
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
