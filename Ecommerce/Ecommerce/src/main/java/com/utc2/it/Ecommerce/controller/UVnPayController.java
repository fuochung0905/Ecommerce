package com.utc2.it.Ecommerce.controller;


import com.utc2.it.Ecommerce.dto.OrderRequest;
import com.utc2.it.Ecommerce.dto.OrderedRequest;
import com.utc2.it.Ecommerce.entity.CartDetail;
import com.utc2.it.Ecommerce.repository.CartDetailRepository;
import com.utc2.it.Ecommerce.service.OrderService;
import com.utc2.it.Ecommerce.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("api/user/vn-pay")
@RequiredArgsConstructor
public class UVnPayController {
    private final VnPayService vnPayService;
    private final CartDetailRepository cartDetailRepository;
    private final HttpServletRequest request;
    private final OrderService orderService;
    @PostMapping("/payment")
    public ResponseEntity<String> submitOrder(@RequestBody OrderedRequest orderRequest){
        CartDetail cartDetail=cartDetailRepository.findById(orderRequest.getId()).orElseThrow();
        int orderTotal= (int) (cartDetail.getPrice()*cartDetail.getQuantity());
        String orderInfo="Thanh toan hoa don :"+cartDetail.getId();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
       String result= vnPayService.createOrder(orderRequest.getCartId(),orderRequest.getDeliveryId(),orderTotal, orderInfo, baseUrl);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @GetMapping("/vnpay-payment")
    public void handleVNPayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int paymentStatus = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String status = paymentStatus == 1 ? "success" : "fail";
//        if(paymentStatus==1){
//            OrderRequest orderRequest= new OrderRequest();
//            Long cartId=Long.parseLong(orderInfo);
//            orderRequest.setCartid(cartId);
//            orderRequest.setPaymentId(2L);
//            OrderRequest result=orderService.userOrder(orderRequest);
//
//        }
        String redirectUrl = String.format(
                "http://localhost:4200/paymentSuccessfully?status=%s&orderInfo=%s&totalPrice=%s&paymentTime=%s&transactionId=%s",
                status, orderInfo, totalPrice, paymentTime, transactionId
        );
        response.sendRedirect(redirectUrl);
    }

}
