package com.example.scripto.response.buyer.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyerBookResponseByBookName {
    private String authorName;
    private Double price;
    private int availableQuantity;
    private String bookDetails;
    private String imageUrl;
}
