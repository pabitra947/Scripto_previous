package com.example.scripto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseByBookNameDto {
    private Long bookId;
    private String authorName;
    private Double price;
    private int totalQuantity;
    private int soldQuantity;
    private int availableQuantity;
    private String bookDetails;
    private String imageUrl;
    private LocalDateTime createdDateAndTime;
}
