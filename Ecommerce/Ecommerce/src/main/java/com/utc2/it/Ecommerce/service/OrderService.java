package com.utc2.it.Ecommerce.service;
import com.utc2.it.Ecommerce.dto.*;

import java.util.List;

public interface OrderService {
    OrderRequest userOrder(OrderRequest request);
    List<UserCartDto> historyOrdered();
    List<UserCartDto> historyOrderApproved();
    List<UserCartDto> historyOrderTransport();
    List<UserCartDto> historyOrderDelivered();
    List<UserCartDto> historyOrderCancel();
    List<UserCartDto> historyOrderedByOrdered();
    AddressDto getAddressByUser();

    List<UserCartDto> AhistoryOrdered();
    List<UserCartDto> AhistoryOrderApproved();
    List<UserCartDto> AhistoryOrderTransport();
    List<UserCartDto> AhistoryOrderDelivered();
    List<UserCartDto> AhistoryOrderCancel();
    List<UserCartDto> AhistoryOrderedByOrdered();
    String OrderedToApproval(OrderedRequest orderRequest);
    String ApprovalToTransport(OrderedRequest orderRequest);
    String TransportToDelivered(OrderedRequest orderRequest);
    Double getTotalAmountByUser(Long userId);
    List<UserCartDto> historyOrderedByUser(Long userId);



}
