package com.example.scripto.controller.buyer;

import com.example.scripto.implementation.BuyerWishlistServiceImpl;
import com.example.scripto.response.buyer.book.BuyerBookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyer-book-wishlist")
public class BuyerWishlistController {

    @Autowired
    private BuyerWishlistServiceImpl wishlistService;



    //  Add a book to wishlist
    @PostMapping("/add-wishlist/{bookId}")
    public ResponseEntity<?> addToWishlist(@PathVariable Long bookId) {
        return wishlistService.addToWishlist(bookId);
    }



    //  Get all wishlist books for a user
    @GetMapping("/get-all-wishlist")
    public ResponseEntity<List<BuyerBookResponse>> getWishlist() {
        return wishlistService.getUserWishlist();
    }



    //  Remove book from wishlist
    @DeleteMapping("/remove-from-wishlist/{bookId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long bookId) {
        return wishlistService.removeFromWishlist(bookId);
    }
}
