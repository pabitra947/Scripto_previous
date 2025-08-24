package com.example.scripto.dto;

import lombok.Data;

@Data
public class BuyerUpdateCartItemDto {
    private Long cartItemId;
    private int quantity;
}
