package com.example.scripto.response.buyer;

import com.example.scripto.dto.BuyerCartItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyerCartResponse {

    private List<BuyerCartItemResponse> items;  // List of items in the cart
    private double totalPrice;             // Total price of all items
}
