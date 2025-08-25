package com.example.scripto.response.buyer.order;

import com.example.scripto.entity.OrderItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemResponse {
    private Long orderItemId;
    private Long bookId;
    private String bookName;
    private Double price;
    private int quantity;
    private OrderItemStatus status;
}
