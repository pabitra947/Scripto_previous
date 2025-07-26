package com.example.scripto.service;

import com.example.scripto.dto.BuyerCartItemDto;
import com.example.scripto.response.buyer.BuyerCartResponseDto;
import org.springframework.http.ResponseEntity;

public interface IBuyerCart {

    ResponseEntity<BuyerCartItemDto> addToCart(Long buyerId, Long bookId, int quantity);

    ResponseEntity<BuyerCartResponseDto> getCartItemsWithTotal(Long buyerId);

    ResponseEntity<String> updateCartItem(Long buyerId, Long bookId, int quantity);

    ResponseEntity<String> removeCartItem(Long buyerId, Long bookId);
}
