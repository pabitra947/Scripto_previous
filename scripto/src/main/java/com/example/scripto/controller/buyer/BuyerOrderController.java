package com.example.scripto.controller.buyer;

import com.example.scripto.entity.Order;
import com.example.scripto.implementation.OrderServiceImpl;
import com.example.scripto.response.buyer.order.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;


    //Used to placed the order from the cart
    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder() {
        return orderService.placeOrder();
    }


    //used to get the order state and details
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        return orderService.getOrderDetails();
    }
}
