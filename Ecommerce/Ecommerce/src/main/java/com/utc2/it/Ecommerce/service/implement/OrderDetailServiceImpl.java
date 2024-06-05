package com.utc2.it.Ecommerce.service.implement;

import com.utc2.it.Ecommerce.dto.OrderDetailDto;
import com.utc2.it.Ecommerce.entity.*;
import com.utc2.it.Ecommerce.repository.OrderDetailRepository;
import com.utc2.it.Ecommerce.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetailDto getInformationOrderByOrderDetailId(Long orderDetailId) {
        OrderDetail orderDetail=orderDetailRepository.findById(orderDetailId).orElseThrow();
        Order order=orderDetail.getOrder();
        ProductItem productItem=orderDetail.getProductItem();
        Product product=productItem.getProduct();
        OrderDetailDto orderDetailDto= new OrderDetailDto();
        User user= order.getUser();
        orderDetailDto.setUserId(user.getId());
        orderDetailDto.setOrderDetailId(orderDetailId);
        orderDetailDto.setUserName(user.getFirstName()+" "+user.getLastName());
        orderDetailDto.setEmail(user.getEmail());
        orderDetailDto.setPhoneNumber(user.getPhoneNumber());
        orderDetailDto.setAddressUser(orderDetail.getAddressUser());
        orderDetailDto.setColor(orderDetail.getColor());
        orderDetailDto.setSize(orderDetail.getSize());
        orderDetailDto.setProductId(product.getId());
        orderDetailDto.setProductName(product.getProductName());
        orderDetailDto.setOrderDelivery("Giao hàng tiêu chuẩn");
        orderDetailDto.setOrderPayment(order.getPayment().getName());
        orderDetailDto.setOrderStatus(order.getOrderStatus().name());
        orderDetailDto.setCreateDate(order.getCreateDate().toString());
        orderDetailDto.setUpdateDate(order.getUpdateDate().toString());
        orderDetailDto.setQuantity(orderDetail.getQuantity());
        orderDetailDto.setPrice(orderDetail.getPrice());
        return orderDetailDto;
    }
}
