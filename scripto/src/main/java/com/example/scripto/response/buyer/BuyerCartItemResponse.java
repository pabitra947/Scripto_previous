package com.example.scripto.response.buyer;

import com.example.scripto.entity.BookListing;
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
    private int availableQuantity;   // Remaining in stock
    private Double totalPrice;
}
