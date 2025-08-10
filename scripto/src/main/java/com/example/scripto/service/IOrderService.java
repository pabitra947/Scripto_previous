package com.example.scripto.service;

import com.example.scripto.entity.Order;
import com.example.scripto.entity.OrderItemStatus;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    Order placeOrder(); // builds an order from buyer's cart
    ResponseEntity<String> updateOrderItemStatus(Long orderItemId, OrderItemStatus newStatus);
}
