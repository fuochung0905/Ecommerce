package com.utc2.it.Ecommerce.service;
import com.utc2.it.Ecommerce.dto.AddressDto;
import com.utc2.it.Ecommerce.dto.OrderRequest;
import com.utc2.it.Ecommerce.dto.UserCartDto;
import java.util.List;

public interface OrderService {
    OrderRequest userOrder(OrderRequest request);
    List<UserCartDto> historyOrdered();
    List<UserCartDto> historyOrderApproved();
    List<UserCartDto> historyOrderTransport();
    List<UserCartDto> historyOrderDelivered();
    List<UserCartDto> historyOrderCancel();
    AddressDto getAddressByUser();
}
