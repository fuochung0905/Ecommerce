package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.utc2.it.Ecommerce.dto.AddressDto;
import com.utc2.it.Ecommerce.dto.OrderRequest;
import com.utc2.it.Ecommerce.dto.UserCartDto;
import com.utc2.it.Ecommerce.exception.NotFoundException;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final CartDetailRepository cartDetailRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductItemRepository productItemRepository;
    private final ExecutorService executorService=new ThreadPoolExecutor(1,1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>());

    @Override
    public OrderRequest userOrder(OrderRequest request) {
        OrderRequest orderRequest= new OrderRequest();
        String username=getCurrentUsername();
        User user=getUser(username);
        Address address= addressRepository.getAddressByIsDefine(user,true);
        if(address !=null){
            CartDetail cartDetail=cartDetailRepository.findById(request.getCartid()).orElseThrow();
            ProductItem product =cartDetail.getProductItem();
            Order order= new Order();
            order.setUser(user);
            order.setOrderStatus(OrderStatus.ordered);
            order.setCreateDate(LocalDateTime.now());
            order.setUpdateDate(LocalDateTime.now());
            order.setApproved(true);

            Order saveOrder=orderRepository.save(order);
            OrderDetail orderDetail= new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setAddressUser(address.getStreet()+" "+address.getState()+" "+address.getCity()+" "+address.getCountry());
            orderDetail.setProductItem(product);
            orderDetail.setPrice(cartDetail.getPrice());
            orderDetail.setColor(cartDetail.getColor());
            orderDetail.setSize(cartDetail.getSize());
            orderDetail.setQuantity(cartDetail.getQuantity());
            orderDetail.setId(saveOrder.getId());
            OrderDetail saveOrderDetail=orderDetailRepository.save(orderDetail);
            if(saveOrderDetail!=null){
                Order tampOrder=orderRepository.findById(saveOrderDetail.getId()).orElseThrow();
                tampOrder.setTotalPrice(saveOrderDetail.getPrice()* saveOrderDetail.getQuantity());
                product.setQyt_stock(product.getQyt_stock()-cartDetail.getQuantity());
                ProductItem saveProduct=productItemRepository.save(product);
                cartDetailRepository.delete(cartDetail);

                orderRequest.setMessage("Order Successfully");
                return orderRequest;
            }
            orderRequest.setMessage("Order fail");
            return orderRequest;
        }
        orderRequest.setMessage("user not address");
        return orderRequest;
    }



    @Override
    public List<UserCartDto> historyOrderApproved() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userCartDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderApproved(user,true);
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto userCartDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    userCartDto.setProductName(product.getProductName());
                    userCartDto.setSize(orderDetail.getSize());
//
                    userCartDto.setColor(orderDetail.getColor());
                    userCartDto.setPrice(orderDetail.getPrice());
                    userCartDto.setQuantity(orderDetail.getQuantity());
                    userCartDto.setImage(productItem.getProductItemImage());
                    userCartDtos.add(userCartDto);
                }
                else {
                    return  null;
                }
            }
        }
        return userCartDtos;
    }

    @Override
    public List<UserCartDto> historyOrderTransport() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userCartDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderTransport(user,true);
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto userCartDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    userCartDto.setProductName(product.getProductName());
                    userCartDto.setSize(orderDetail.getSize());
                    userCartDto.setColor(orderDetail.getColor());
                    userCartDto.setPrice(orderDetail.getPrice());
                    userCartDto.setQuantity(orderDetail.getQuantity());
                    userCartDto.setImage(productItem.getProductItemImage());
                    userCartDtos.add(userCartDto);
                }
                else {
                    return  null;
                }
            }
        }
        return userCartDtos;
    }

    @Override
    public List<UserCartDto> historyOrderDelivered() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userCartDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderDelivered(user,true);
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto userCartDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    userCartDto.setProductName(product.getProductName());
                    userCartDto.setSize(orderDetail.getSize());
                    userCartDto.setColor(orderDetail.getColor());
                    userCartDto.setPrice(orderDetail.getPrice());
                    userCartDto.setQuantity(orderDetail.getQuantity());
                    userCartDto.setImage(productItem.getProductItemImage());
                    userCartDtos.add(userCartDto);
                }
                else {
                    return  null;
                }
            }
        }
        return userCartDtos;
    }

    @Override
    public List<UserCartDto> historyOrderCancel() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userCartDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderCancel(user,true);
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto userCartDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    userCartDto.setProductName(product.getProductName());
                    userCartDto.setSize(orderDetail.getSize());
                    userCartDto.setColor(orderDetail.getColor());
                    userCartDto.setPrice(orderDetail.getPrice());
                    userCartDto.setQuantity(orderDetail.getQuantity());
                    userCartDto.setImage(productItem.getProductItemImage());
                    userCartDtos.add(userCartDto);
                }
                else {
                    return  null;
                }
            }
        }
        return userCartDtos;
    }

    @Override
    public List<UserCartDto> historyOrdered() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userCartDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderSuccess(user,true);
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto userCartDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    userCartDto.setProductName(product.getProductName());
                    userCartDto.setSize(orderDetail.getSize());
                    userCartDto.setColor(orderDetail.getColor());
                    userCartDto.setPrice(orderDetail.getPrice());
                    userCartDto.setQuantity(orderDetail.getQuantity());
                    userCartDto.setImage(productItem.getProductItemImage());
                    userCartDtos.add(userCartDto);
                }
                else {
                    return  null;
                }
            }
        }
        return userCartDtos;
    }
    @Override
    public AddressDto getAddressByUser() {
        String username=getCurrentUsername();
        User user=getUser(username);
        Address address=addressRepository.getAddressByIsDefine(user,true);
        if(address!=null){
            AddressDto addressDto= new AddressDto();
            addressDto.setId(address.getId());
            addressDto.setCity(address.getCity());
            addressDto.setStreet(address.getStreet());
            addressDto.setCountry(address.getCountry());
            addressDto.setState(address.getState());
            addressDto.setUserId(address.getUser().getId());
            return addressDto;
        }
        throw  new NotFoundException("User does not have an address yet");
    }

//    private ShoppingCart getCart(User user){
//        ShoppingCart shoppingCart=shoppingCartRepository.findShoppingCartByUser(user);
//        return shoppingCart;
//    }
    private User getUser(String username){
        User user=userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return user;
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // Trả về tên người dùng hiện đang đăng nhập
    }

}
