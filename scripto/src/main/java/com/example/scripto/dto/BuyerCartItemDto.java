package com.example.scripto.dto;


import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BuyerCartItemDto {
    private Long bookId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity = 1;
}