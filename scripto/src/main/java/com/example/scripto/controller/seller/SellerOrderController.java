package com.example.scripto.controller.seller;

import com.example.scripto.entity.OrderItemStatus;
import com.example.scripto.implementation.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/seller/orders")
public class SellerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    // seller changes status for an order item they own
    @PutMapping("/item/{orderItemId}/status")
    public ResponseEntity<String> updateOrderItemStatus(@PathVariable Long orderItemId,
                                                        @RequestParam OrderItemStatus status) {
        return orderService.updateOrderItemStatus(orderItemId, status);
    }
}
