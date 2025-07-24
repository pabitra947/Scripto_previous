package com.example.scripto.response.seller.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerBookResponseByAuthorName {
    private Long bookId;
    private String bookName;
    private Double price;
    private int totalQuantity;
    private int soldQuantity;
    private int availableQuantity;
    private String bookDetails;
    private String imageUrl;
    private LocalDateTime createdDateAndTime;
}
