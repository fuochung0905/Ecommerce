package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.ColorSizeDto;
import com.utc2.it.Ecommerce.dto.ExceptionCartDto;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.exception.NotFoundException;
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
import java.util.Objects;

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

    private final ProductItemVariationOptionRepository productItemVariationOptionRepository;

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
    public ExceptionCartDto addToCart(ColorSizeDto productVariationDtos) {
        ExceptionCartDto exceptionCartDto=new ExceptionCartDto();
        if(productVariationDtos.getVariationOptionId()==0){
            boolean check=false;
            ProductItem productItem=productItemRepository.findById(productVariationDtos.getIdColor()).orElseThrow();
            VariationOption findByColor=variationOptionRepository.findById(productItem.getIdColor()).orElseThrow();
            VariationOption findBySize=variationOptionRepository.findById(productVariationDtos.getVariationOptionId()).orElseThrow();
            ShoppingCart newShoppingCart = new ShoppingCart();
            String currentUsername = getCurrentUsername();
            User user = getUser(currentUsername);
            if (user == null) {
                exceptionCartDto.setMessage("User not found");
            }
            ProductItemVariationOption productItemVariationOption=productItemVariationOptionRepository.findProductItemVariationOptionByProductItemAndVariationOption(productItem,findBySize);
            if(productItemVariationOption.getQuantity()< productVariationDtos.getQuantity()){
                exceptionCartDto.setMessage("not quantity");
                return exceptionCartDto;
            }
            ShoppingCart checkShoppingCart = getCart(user);
            if (checkShoppingCart == null) {
                newShoppingCart.setUser(user);
                shoppingCartRepository.save(newShoppingCart);
                exceptionCartDto.setMessage("Đã tạo giỏ hàng");
            }
          
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
                    exceptionCartDto.setMessage("Thêm thành công");
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
                                exceptionCartDto.setMessage("Thêm thành công");
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
                        exceptionCartDto.setMessage("Thêm thành công");
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
            exceptionCartDto.setMessage("User not found");
        }
        ProductItemVariationOption productItemVariationOption=productItemVariationOptionRepository.findProductItemVariationOptionByProductItemAndVariationOption(productItem,findBySize);
        if(productItemVariationOption.getQuantity()< productVariationDtos.getQuantity()){
            exceptionCartDto.setMessage("not quantity");
            return exceptionCartDto;
        }
        ShoppingCart checkShoppingCart = getCart(user);
        if (checkShoppingCart == null) {
            newShoppingCart.setUser(user);
            shoppingCartRepository.save(newShoppingCart);
            exceptionCartDto.setMessage("Đã tạo giỏ hàng");
        }

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
                exceptionCartDto.setMessage("Thêm thành công");
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
                            exceptionCartDto.setMessage("Thêm thành công");
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
                    exceptionCartDto.setMessage("Thêm thành công");
                }
            }

        return exceptionCartDto;
    }

    @Override
    public void removeCart(ColorSizeDto productVariationDtos) {
        if(productVariationDtos.getVariationOptionId()==0){
            boolean check=false;
            ProductItem productItem=productItemRepository.findById(productVariationDtos.getIdColor()).orElseThrow();
            VariationOption findByColor=variationOptionRepository.findById(productItem.getIdColor()).orElseThrow();

            String currentUsername = getCurrentUsername();
            User user = getUser(currentUsername);
            ShoppingCart checkShoppingCart = getCart(user);
                List<CartDetail>  cartDetails = cartDetailRepository.findCartDetailByShopping_cartAndProduct(checkShoppingCart, productItem);
                    for(CartDetail cartDetail:cartDetails)
                    {
                        if(Objects.equals(cartDetail.getColor(), findByColor.getValue()) )
                        {
                            check=true;
                            if (check) {
                                if(cartDetail.getQuantity()>=1){
                                    cartDetail.setQuantity(cartDetail.getQuantity() - productVariationDtos.getQuantity());
                                   CartDetail save= cartDetailRepository.save(cartDetail);
                                    if(save.getQuantity()==0){
                                        cartDetailRepository.delete(save);
                                    }
                                }
                                else {
                                    cartDetailRepository.delete(cartDetail);
                                }
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


            List<CartDetail>  cartDetails = cartDetailRepository.findCartDetailByShopping_cartAndProduct(checkShoppingCart, productItem);
                for(CartDetail cartDetail:cartDetails)
                {
                    if(Objects.equals(cartDetail.getColor(), findByColor.getValue()) && Objects.equals(cartDetail.getSize(), findBySize.getValue()))
                    {
                        check=true;
                        if (check) {
                            if(cartDetail.getQuantity()>=1){
                                cartDetail.setQuantity(cartDetail.getQuantity() - productVariationDtos.getQuantity());
                                CartDetail save= cartDetailRepository.save(cartDetail);
                                if(save.getQuantity()==0){
                                    cartDetailRepository.delete(save);
                                }
                            }
                            else {
                                cartDetailRepository.delete(cartDetail);
                            }
                        }

                    }
                }



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
                userCartDto.setProductItemId(cart.getProductItem().getId());
                userCartDto.setSize(cart.getSize());
                userCartDto.setColor(cart.getColor());
                userCartDto.setQuantity(cart.getQuantity());
                userCartDto.setPrice(cart.getPrice());
                userCartDto.setImage(productItem.getProductItemImage());
                userCartDtoS.add(userCartDto);
            }
            return userCartDtoS;
        }
       return null;
    }
    @Override
    public Integer getCartCount() {
        String username=getCurrentUsername();
        User user=getUser(username);
        Integer cartItemCount=cartDetailRepository.GetCartItemCount(user);
        return cartItemCount;
    }

    @Override
    public void deleteCartById(Long cartId) {
        CartDetail cartDetail=cartDetailRepository.findById(cartId).orElseThrow();
        cartDetailRepository.delete(cartDetail);
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
