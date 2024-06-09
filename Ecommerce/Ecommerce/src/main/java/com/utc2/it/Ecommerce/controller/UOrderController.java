package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.utc2.it.Ecommerce.dto.AddressDto;
import com.utc2.it.Ecommerce.dto.OrderRequest;
import com.utc2.it.Ecommerce.dto.UserCartDto;
import com.utc2.it.Ecommerce.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/order")
public class UOrderController {
    private final OrderService orderService;
    @PostMapping("/createNewOrder")
    public ResponseEntity<OrderRequest>addOrder(@RequestBody OrderRequest request){
        OrderRequest result=orderService.userOrder(request);
        return  new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/history")
    public ResponseEntity<List<UserCartDto>>getOrderHistory(){
        List<UserCartDto>result=orderService.historyOrdered();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/historyOrdered")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryOrdered(){
        List<UserCartDto>result=orderService.historyOrderedByOrdered();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/historyApproved")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryApproved(){
        List<UserCartDto>result=orderService.historyOrderApproved();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/historyTransport")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryTransport(){
        List<UserCartDto>result=orderService.historyOrderTransport();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/historyDelivered")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryDelivered(){
        List<UserCartDto>result=orderService.historyOrderDelivered();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/historyCancel")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryCancel(){
        List<UserCartDto>result=orderService.historyOrderCancel();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/currentAddress")
    public ResponseEntity<AddressDto>getAddressCurrentUser(){
        AddressDto addressDto=orderService.getAddressByUser();

        return new ResponseEntity<>(addressDto,HttpStatus.OK);
    }
}
