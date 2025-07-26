package com.example.scripto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerCartItemDto {
    private Long cartItemId;

    private Long bookId;
    private String bookName;
    private String authorName;
    private Double price;
    private int availableQuantity;
    private String imageUrl;

    private int quantity;
}