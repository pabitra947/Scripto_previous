package com.example.scripto.controller.buyer;

import com.example.scripto.entity.Order;
import com.example.scripto.implementation.OrderServiceImpl;
import com.example.scripto.response.buyer.order.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;


    //Used to placed the order from the cart
    @PostMapping("/place/{addressId}")
    public ResponseEntity<String> placeOrder(@PathVariable Long addressId) {
        return orderService.placeOrder(addressId);
    }


    //used to get the order state and details
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        return orderService.getOrderDetails();
    }
}
