package com.example.scripto.service;

import com.example.scripto.response.buyer.book.BuyerBookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBuyerWishList {

    ResponseEntity<?> addToWishlist(Long bookId);

    ResponseEntity<List<BuyerBookResponse>> getUserWishlist();

    ResponseEntity<String> removeFromWishlist(Long bookId);

}
