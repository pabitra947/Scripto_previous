package com.example.scripto.service;

import com.example.scripto.entity.Wishlist;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IWishList {

    ResponseEntity<Wishlist> addToWishlist(Long userId, Long bookId);

    ResponseEntity<List<Wishlist>> getUserWishlist(Long userId);

    void removeFromWishlist(Long userId, Long bookId);

}
