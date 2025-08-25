package com.example.scripto.service;

import com.example.scripto.dto.BuyerCartItemDto;
import com.example.scripto.dto.BuyerUpdateCartItemDto;
import com.example.scripto.response.buyer.book.BuyerCartResponse;
import org.springframework.http.ResponseEntity;

public interface IBuyerCart {

    ResponseEntity<String> addToCart(BuyerCartItemDto request);

    ResponseEntity<BuyerCartResponse> viewCart();

    ResponseEntity<String> updateCartItem(BuyerUpdateCartItemDto updateCartItemDto);

    ResponseEntity<String> removeCartItem(Long cartItemId);
}
