package com.example.scripto.controller;

import com.example.scripto.entity.Wishlist;
import com.example.scripto.implementation.WishlistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist-book")
public class WishlistController {

    @Autowired
    private WishlistServiceImpl wishlistService;



    //  Add a book to wishlist
    @PostMapping("/add-wishlist")
    public ResponseEntity<Wishlist> addToWishlist(@RequestParam Long userId,@RequestParam Long bookId) {
        return wishlistService.addToWishlist(userId, bookId);
    }



    //  Get all wishlist books for a user
    @GetMapping("/get-all-/{userId}")
    public ResponseEntity<List<Wishlist>> getWishlist(@PathVariable Long userId) {
        return wishlistService.getUserWishlist(userId);
    }



    //  Remove book from wishlist
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromWishlist(
            @RequestParam Long userId,
            @RequestParam Long bookId) {
        wishlistService.removeFromWishlist(userId, bookId);
        return ResponseEntity.ok("Removed from wishlist");
    }
}
