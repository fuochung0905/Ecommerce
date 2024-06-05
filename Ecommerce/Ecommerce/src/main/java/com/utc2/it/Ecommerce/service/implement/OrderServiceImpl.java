package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.*;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.*;
import com.utc2.it.Ecommerce.service.OrderService;
import com.utc2.it.Ecommerce.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final ProductItemVariationOptionRepository productItemVariationOptionRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final VariationOptionRepository variationOptionRepository;
    private final ProductItemRepository productItemRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    @Transactional
    @Override
    public OrderRequest userOrder(OrderRequest request) {
        OrderRequest orderRequest= new OrderRequest();
        String username=getCurrentUsername();
        User user=getUser(username);
        Address address= addressRepository.getAddressByIsDefine(user,true);
        if(address !=null){
            if(user.getPhoneNumber()==null){
                orderRequest.setMessage("user not phoneNumber");
                return orderRequest;
            }
            CartDetail cartDetail=cartDetailRepository.findById(request.getCartid()).orElseThrow();
            ProductItem product =cartDetail.getProductItem();
            VariationOption findIdSize=variationOptionRepository.findById(cartDetail.getIdSize()).orElseThrow();
            ProductItemVariationOption tamp=productItemVariationOptionRepository.findProductItemVariationOptionByProductItemAndVariationOption(product,findIdSize);
            if(tamp.getQuantity()<cartDetail.getQuantity()){
                orderRequest.setMessage("not quantity");
                return  orderRequest;
            }
            Order order= new Order();
            order.setUser(user);

            if(request.getPaymentId()!=0){
                Payment payment= paymentRepository.findPaymentByName("Thanh toán khi nhận hàng");
                order.setPayment(payment);
            }
            else {
                PaymentType paymentType=paymentTypeRepository.findById(request.getPaymentTypeId()).orElseThrow();
                Payment payment=paymentType.getPayment();
                order.setPayment(payment);
            }
            order.setOrderStatus(OrderStatus.ordered);
            order.setCreateDate(LocalDateTime.now());
            order.setUpdateDate(LocalDateTime.now());
            order.setOrdered(true);
            Order saveOrder=orderRepository.save(order);
            VariationOption variationOption=variationOptionRepository.findById(cartDetail.getIdSize()).orElseThrow();
            ProductItem productItem=productItemRepository.findById(cartDetail.getProductItem().getId()).orElseThrow();
            ProductItemVariationOption productItemVariationOption=productItemVariationOptionRepository.findProductItemVariationOptionByProductItemAndVariationOption(cartDetail.getProductItem(),variationOption);
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
                tampOrder.setTotalPrice((saveOrderDetail.getPrice()* saveOrderDetail.getQuantity())+20000);
                Product product1=productItem.getProduct();
                productItemVariationOption.setQuantity(productItemVariationOption.getQuantity()-cartDetail.getQuantity());
               productItemVariationOptionRepository.save(productItemVariationOption);
                productItem.setQyt_stock(productItem.getQyt_stock()-saveOrderDetail.getQuantity());
                productItemRepository.save(productItem);
                product1.setQuantity(product1.getQuantity()-saveOrderDetail.getQuantity());
                productRepository.save(product1);
                cartDetailRepository.delete(cartDetail);
                orderRequest.setMessage("Order Successfully");
                return orderRequest;
            }
        }
        orderRequest.setMessage("user not address");
        return orderRequest;
    }
    @Override
    public List<UserCartDto> historyOrderApproved() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderApproved(user,true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setSize(orderDetail.getSize());
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setId(orderDetail.getId());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public List<UserCartDto> historyOrderTransport() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderTransport(user,true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setSize(orderDetail.getSize());
                    orderDto.setId(orderDetail.getId());
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public List<UserCartDto> historyOrderDelivered() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderDelivered(user,true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setSize(orderDetail.getSize());
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setId(orderDetail.getId());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public List<UserCartDto> historyOrderCancel() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderCancel(user,true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setSize(orderDetail.getSize());
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    orderDto.setPrice(orderDetail.getPrice());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    orderDto.setQuantity(orderDetail.getQuantity());
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setId(orderDetail.getId());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;

    }

    @Override
    public List<UserCartDto> historyOrdered() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderAll(user);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setSize(orderDetail.getSize());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    orderDto.setId(orderDetail.getId());
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
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
       return null;
    }

    @Override
    public List<UserCartDto> AhistoryOrdered() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderWithOrderAll();
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setId(order.getId());
                    orderDto.setSize(orderDetail.getSize());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setPrice(orderDetail.getPrice());

                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public List<UserCartDto> AhistoryOrderApproved() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderWithOrderApproved(true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setId(order.getId());
                    orderDto.setSize(orderDetail.getSize());
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public List<UserCartDto> AhistoryOrderTransport() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderWithOrderTransport(true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setId(order.getId());;
                    orderDto.setSize(orderDetail.getSize());
                    orderDto.setImage(productItem.getProductItemImage());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    orderDto.setColor(orderDetail.getColor());
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public List<UserCartDto> AhistoryOrderDelivered() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderWithOrderDelivered(true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setId(order.getId());
                    orderDto.setSize(orderDetail.getSize());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public List<UserCartDto> AhistoryOrderCancel() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderWithOrderCancel(true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setId(order.getId());
                    orderDto.setSize(orderDetail.getSize());
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public List<UserCartDto> AhistoryOrderedByOrdered() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderWithOrderBOrderByOrdered(true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setProductName(product.getProductName());
                    orderDto.setId(order.getId());
                    orderDto.setSize(orderDetail.getSize());
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
    }

    @Override
    public String OrderedToApproval(OrderedRequest orderRequest) {
        OrderDetail orderDetail=orderDetailRepository.findById(orderRequest.getId()).orElseThrow();
        Order order=orderDetail.getOrder();
        order.setApproved(true);
        order.setOrdered(false);
        order.setOrderStatus(OrderStatus.approved);
        orderRepository.save(order);
        return "Approved";
    }

    @Override
    public String ApprovalToTransport(OrderedRequest orderRequest) {
        OrderDetail orderDetail=orderDetailRepository.findById(orderRequest.getId()).orElseThrow();
        Order order=orderDetail.getOrder();
        order.setApproved(false);
        order.setTransport(true);
        order.setOrderStatus(OrderStatus.transport);
        orderRepository.save(order);
        return "Transport";
    }

    @Override
    public String TransportToDelivered(OrderedRequest orderRequest) {
        OrderDetail orderDetail=orderDetailRepository.findById(orderRequest.getId()).orElseThrow();
        Order order=orderDetail.getOrder();
        order.setDelivered(true);
        order.setTransport(false);
        order.setOrderStatus(OrderStatus.delivered);
        orderRepository.save(order);
        return "Delivered";
    }

    @Override
    public Double getTotalAmountByUser(Long userId) {
        User user=userRepository.findById(userId).orElseThrow();
        Double totalAmount=orderRepository.getTotalAmount(user);
        return totalAmount;
    }

    @Override
    public List<UserCartDto> historyOrderedByUser(Long userId) {
        User user=userRepository.findById(userId).orElseThrow();
        List<UserCartDto>userOrderDtos= new ArrayList<>();
     List<Order>historyOrder=orderRepository.findAllOrderByUserWithOrderAll(user);
     for (Order order:historyOrder) {
        for (OrderDetail orderDetail:order.getOrderDetails()) {
            if(orderDetail!=null){
                UserCartDto orderDto= new UserCartDto();
                ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                orderDto.setProductName(product.getProductName());
                orderDto.setId(order.getId());
                orderDto.setSize(orderDetail.getSize());
                orderDto.setImage(productItem.getProductItemImage());
                orderDto.setColor(orderDetail.getColor());
                User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                orderDto.setPaymentId(payment.getId());
                if(payment.getId()!=1){
                    orderDto.setPaymentStatus("Đã thanh toán");
                }
                else {
                    orderDto.setPaymentStatus("Chưa thanh toán");
                }
                orderDto.setAddressUser(orderDetail.getAddressUser());
                orderDto.setPrice(orderDetail.getPrice());
                orderDto.setQuantity(orderDetail.getQuantity());
                orderDto.setOrderStatus(order.getOrderStatus().toString());
                orderDto.setOrderDate(order.getUpdateDate().toString());
                userOrderDtos.add(orderDto);
            }
        }
     }
     return userOrderDtos;
}

    @Override
    public Integer countApproval() {
        Integer result=orderRepository.getCountApproval(true);
        return result;
    }

    @Override
    public List<UserCartDto> historyOrderedByOrdered() {
        String username=getCurrentUsername();
        User user= getUser(username);
        List<UserCartDto>userOrderDtos= new ArrayList<>();
        List<Order>orderList=orderRepository.findAllOrderByUserWithOrderBOrderByOrdered(user,true);
        if(orderList==null|| orderList.isEmpty()){
            return null;
        }
        for (Order order:orderList) {
            for (OrderDetail orderDetail:order.getOrderDetails()) {
                if(orderDetail!=null){
                    UserCartDto orderDto= new UserCartDto();
                    ProductItem productItem=productItemRepository.findById(orderDetail.getProductItem().getId()).orElseThrow();
                    Product product=productRepository.findById(productItem.getProduct().getId()).orElseThrow();
                    orderDto.setId(order.getId());
                    orderDto.setProductName(product.getProductName());
                    orderDto.setSize(orderDetail.getSize());
                    orderDto.setImage(productItem.getProductItemImage());
                    orderDto.setColor(orderDetail.getColor());
                    Payment payment=paymentRepository.findById(orderDetail.getOrder().getPayment().getId()).orElseThrow();
                    orderDto.setPaymentId(payment.getId());
                    if(payment.getId()!=1){
                        orderDto.setPaymentStatus("Đã thanh toán");
                    }
                    else {
                        orderDto.setPaymentStatus("Chưa thanh toán");
                    }
                    User orderUser=userRepository.findById(orderDetail.getOrder().getUser().getId()).orElseThrow();
                    orderDto.setUsername(orderUser.getFirstName()+" "+orderUser.getLastName());
                    orderDto.setAddressUser(orderDetail.getAddressUser());
                    orderDto.setPrice(orderDetail.getPrice());
                    orderDto.setQuantity(orderDetail.getQuantity());
                    orderDto.setOrderStatus(order.getOrderStatus().toString());
                    orderDto.setOrderDate(order.getUpdateDate().toString());
                    userOrderDtos.add(orderDto);
                }
            }
        }
        return userOrderDtos;
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
