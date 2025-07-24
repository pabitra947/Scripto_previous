package com.example.scripto.implementation;

import com.example.scripto.entity.BookListing;
import com.example.scripto.entity.User;
import com.example.scripto.entity.Wishlist;
import com.example.scripto.repository.BuyerBookListingRepo;
import com.example.scripto.repository.UserRepository;
import com.example.scripto.repository.WishlistRepository;
import com.example.scripto.service.IWishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WishlistServiceImpl implements IWishList {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuyerBookListingRepo bookListingRepo;

    // Add to wishlist
    @Override
    public ResponseEntity<Wishlist> addToWishlist(Long userId, Long bookId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            BookListing book = bookListingRepo.findById(bookId)
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            Wishlist existing = wishlistRepository.findWishlistItem(userId, bookId);
            if (existing != null) {
                throw new RuntimeException("Already in wishlist");
            }

            Wishlist wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist.setBook(book);
            wishlist.setAddedAt(LocalDateTime.now());

            return new ResponseEntity<>(wishlistRepository.save(wishlist), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    // View wishlist of a user
    @Override
    public ResponseEntity<List<Wishlist>> getUserWishlist(Long userId) {
        try {
            List<Wishlist> wishlists = wishlistRepository.findAllByUserId(userId);
            return new ResponseEntity<>(wishlists, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }




    // Remove from wishlist
    @Override
    public void removeFromWishlist(Long userId, Long bookId) {
        Wishlist existing = wishlistRepository.findWishlistItem(userId, bookId);
        if (existing == null) {
            throw new RuntimeException("Book not found in wishlist");
        }
        wishlistRepository.delete(existing);
    }
}
