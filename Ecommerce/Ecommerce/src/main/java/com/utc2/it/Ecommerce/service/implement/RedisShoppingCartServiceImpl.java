package com.utc2.it.Ecommerce.service.implement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.utc2.it.Ecommerce.dto.CartDto;
import com.utc2.it.Ecommerce.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RedisShoppingCartServiceImpl implements RedisShoppingCartService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductItemRepository productItemRepository;
    private static final String SHOPPING_CART_KEY_PREFIX = "shopping_cart:";
    private final CartDetailRepository cartDetailRepository;
    private ShoppingCart getCart(User user){
        ShoppingCart shoppingCart=shoppingCartRepository.findShoppingCartByUser(user);
        return shoppingCart;
    }
    @Override
    public void addToCart(Long  productId, CartDto cartDto) {
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
           throw new NotFoundException("User not found");
        }
        String key = SHOPPING_CART_KEY_PREFIX + user.getId().toString();
        ShoppingCart checkShoppingCart = getCart(user);
        if (checkShoppingCart == null) {
            newShoppingCart.setUser(user);
            shoppingCartRepository.save(newShoppingCart);
        }
        else {

            ProductItem productItem=productItemRepository.findById(productId).orElseThrow();
            CartDetail cartDetail = cartDetailRepository.findCartDetailByShopping_cartAndProduct(checkShoppingCart, productItem);
            if(cartDetail==null) {
                CartDetail newCart= new CartDetail();
                newCart.setQuantity(1);
                newCart.setProductItem(productItem);
                newCart.setSize(cartDto.getSize());
                newCart.setColor(cartDto.getColor());
                double totalPrice = productItem.getPrice() * 1;
                newCart.setPrice(totalPrice);
                newCart.setShopping_cart(checkShoppingCart);
                CartDetail save= cartDetailRepository.save(newCart);
                // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
                if (redisTemplate.opsForHash().hasKey(key, productId.toString())) {
                    String existItemJSON = (String) redisTemplate.opsForHash().get(key, productId.toString());
                    if (existItemJSON != null) {
                        try {
                            // Chuyển đổi chuỗi JSON thành đối tượng ShoppingCartItem
                            ShoppingCartItem exist = objectMapper.readValue(existItemJSON, ShoppingCartItem.class);
                            exist.setQuantity(exist.getQuantity() + 1);
                            // Chuyển đối tượng ShoppingCartItem thành chuỗi JSON trước khi lưu vào Redis
                            String updatedItemJSON = objectMapper.writeValueAsString(exist);
                            redisTemplate.opsForHash().put(key, productId.toString(), updatedItemJSON);
                        } catch (Exception e) {
                            e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi chuyển đổi dữ liệu từ JSON thành đối tượng
                        }
                    }
                } else {
                    // Tạo mới đối tượng ShoppingCartItem
                    ShoppingCartItem cartItem = new ShoppingCartItem();
                    cartItem.setId(save.getId());
                    ProductItem tamp=productItemRepository.findById(save.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(tamp.getProduct().getId()).orElseThrow();
                    cartItem.setProductName(product.getProductName());
                    cartItem.setPrice(save.getPrice());
                    cartItem.setUserId(user.getId());
                    cartItem.setQuantity(1);
                    cartItem.setProductId(productId);
                    cartItem.setSize(cartDto.getSize());
                    cartItem.setColor(cartDto.getColor());
                    cartItem.setProductImageName(productItem.getProductItemImage());
                    try {
                        // Chuyển đối tượng ShoppingCartItem thành chuỗi JSON trước khi lưu vào Redis
                        String cartItemJSON = objectMapper.writeValueAsString(cartItem);
                        redisTemplate.opsForHash().put(key, product.getId().toString(), cartItemJSON);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi chuyển đổi đối tượng thành chuỗi JSON
                    }
                }
            }
            else {
                cartDetail.setQuantity(cartDetail.getQuantity()+1);
              CartDetail save=  cartDetailRepository.save(cartDetail);
                // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
                if (redisTemplate.opsForHash().hasKey(key, productId.toString())) {
                    String existItemJSON = (String) redisTemplate.opsForHash().get(key, productId.toString());
                    if (existItemJSON != null) {
                        try {
                            // Chuyển đổi chuỗi JSON thành đối tượng ShoppingCartItem
                            ShoppingCartItem exist = objectMapper.readValue(existItemJSON, ShoppingCartItem.class);
                            exist.setQuantity(exist.getQuantity() + 1);
                            // Chuyển đối tượng ShoppingCartItem thành chuỗi JSON trước khi lưu vào Redis
                            String updatedItemJSON = objectMapper.writeValueAsString(exist);
                            redisTemplate.opsForHash().put(key, productId.toString(), updatedItemJSON);
                        } catch (Exception e) {
                            e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi chuyển đổi dữ liệu từ JSON thành đối tượng
                        }
                    }
                } else {
                    // Tạo mới đối tượng ShoppingCartItem
                    ShoppingCartItem cartItem = new ShoppingCartItem();
                    cartItem.setId(save.getId());
                    ProductItem tamp=productItemRepository.findById(save.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(tamp.getProduct().getId()).orElseThrow();
                    cartItem.setProductName(product.getProductName());
                    cartItem.setPrice(save.getPrice());
                    cartItem.setUserId(user.getId());
                    cartItem.setQuantity(1);
                    cartItem.setProductId(productId);
                    cartItem.setSize(cartDto.getSize());
                    cartItem.setColor(cartDto.getColor());
                    cartItem.setProductImageName(productItem.getProductItemImage());
                    try {
                        // Chuyển đối tượng ShoppingCartItem thành chuỗi JSON trước khi lưu vào Redis
                        String cartItemJSON = objectMapper.writeValueAsString(cartItem);
                        redisTemplate.opsForHash().put(key, product.getId().toString(), cartItemJSON);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi chuyển đổi đối tượng thành chuỗi JSON
                    }
                }
            }
        }
    }

    @Override
    public void removeCart(Long productId) {
        String username = getCurrentUsername();
        User user = getUser(username);
        String key = SHOPPING_CART_KEY_PREFIX + user.getId();
        if(redisTemplate.opsForHash().hasKey(key,productId.toString())){
            String existItem=(String) redisTemplate.opsForHash().get(key,productId.toString());
            if (existItem!=null){
                try {
                    ShoppingCartItem shoppingCartItem=objectMapper.readValue(existItem,ShoppingCartItem.class);
                    if(shoppingCartItem.getQuantity()>1){
                        shoppingCartItem.setQuantity(shoppingCartItem.getQuantity()-1);
                            String updateShoppingJson=objectMapper.writeValueAsString(shoppingCartItem);
                            redisTemplate.opsForHash().put(key,productId.toString(),updateShoppingJson);
                    }
                    else {
                    redisTemplate.opsForHash().delete(key,productId.toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else {
            throw  new NotFoundException("Not found product in shopping_cart");
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
