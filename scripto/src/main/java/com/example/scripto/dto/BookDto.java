package com.example.scripto.dto;

import lombok.Data;

@Data
public class BookDto {
    private String bookName;
    private Long totalQuantity;
    private Double price;
    private String authorName;
    private String imageUrl;
    private String bookDetails;
}
