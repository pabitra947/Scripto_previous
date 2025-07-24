package com.example.scripto.response.buyer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerBookResponse {
    private String bookName;
    private String authorName;
    private Double price;
    private int availableQuantity;
    private String bookDetails;
    private String imageUrl;
}
