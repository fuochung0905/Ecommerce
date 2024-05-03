package com.utc2.it.Ecommerce.controller;

import com.utc2.it.Ecommerce.dto.OrderedRequest;
import com.utc2.it.Ecommerce.dto.UserCartDto;
import com.utc2.it.Ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/order")
public class AOrderController {
    private final OrderService orderService;
    @GetMapping("/history")
    public ResponseEntity<List<UserCartDto>> getOrderHistory(){
        List<UserCartDto>result=orderService.AhistoryOrdered();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/historyOrdered")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryOrdered(){
        List<UserCartDto>result=orderService.AhistoryOrderedByOrdered();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/historyApproved")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryApproved(){
        List<UserCartDto>result=orderService.AhistoryOrderApproved();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/historyTransport")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryTransport(){
        List<UserCartDto>result=orderService.AhistoryOrderTransport();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/historyDelivered")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryDelivered(){
        List<UserCartDto>result=orderService.AhistoryOrderDelivered();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @GetMapping("/historyCancel")
    public ResponseEntity<List<UserCartDto>>getOrderHistoryCancel(){
        List<UserCartDto>result=orderService.AhistoryOrderCancel();
        if(result==null){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping("/orderedToApproval")
    public ResponseEntity<String> orderToApproval(@RequestBody OrderedRequest orderedRequest){
        String result=orderService.OrderedToApproval(orderedRequest);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping("/approvalToTransport")
    public ResponseEntity<String> approvalToTransport(@RequestBody OrderedRequest orderedRequest){
        String result=orderService.ApprovalToTransport(orderedRequest);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PostMapping("/transportToDelivered")
    public ResponseEntity<String> transportToDelivered(@RequestBody OrderedRequest orderedRequest){
        String result=orderService.TransportToDelivered(orderedRequest);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
