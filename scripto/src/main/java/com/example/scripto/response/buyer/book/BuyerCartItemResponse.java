package com.example.scripto.response.buyer.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerCartItemResponse {

    private Long bookId;
    private String bookName;
    private String authorName;
    private String bookDetails;
    private Double price;
    private int quantity;            // Quantity in cart
    private Double totalPrice;
}
