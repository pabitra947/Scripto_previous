package com.example.scripto.service;

import com.example.scripto.entity.Order;
import com.example.scripto.entity.OrderItemStatus;
import com.example.scripto.response.buyer.order.OrderResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderService {
    ResponseEntity<String> placeOrder(Long addressId); // builds an order from buyer's cart

    ResponseEntity<String> updateOrderItemStatus(Long orderItemId, OrderItemStatus newStatus);

    ResponseEntity<List<OrderResponse>> getOrderDetails();
}
