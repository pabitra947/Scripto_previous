package com.example.scripto.implementation;

import com.example.scripto.entity.BookListing;
import com.example.scripto.entity.User;
import com.example.scripto.entity.Wishlist;
import com.example.scripto.repository.BuyerBookRepo;
import com.example.scripto.repository.UserRepository;
import com.example.scripto.repository.BuyerWishlistRepository;
import com.example.scripto.response.buyer.book.BuyerBookResponse;
import com.example.scripto.service.IBuyerWishList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BuyerWishlistServiceImpl implements IBuyerWishList {

    @Autowired
    private BuyerWishlistRepository wishlistRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BuyerBookRepo bookRepo;



    // Add to wishlist
    @Override
    public ResponseEntity<?> addToWishlist(Long bookId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User user = userRepo.findByEmail(email);

            if (user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            if (wishlistRepo.existsByUserAndBook_BookId(user, bookId)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Already in wishlist");
            }

            BookListing book = bookRepo.findById(bookId).orElse(null);
            if (book == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
            }

            Wishlist wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist.setBook(book);

            wishlistRepo.save(wishlist);
            return new ResponseEntity<>("Added to wishlist", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    // View wishlist of a user
    @Override
    public ResponseEntity<List<BuyerBookResponse>> getUserWishlist() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User user = userRepo.findByEmail(email);

            if (user == null){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<>());
            }

            List<Wishlist> wishlist = wishlistRepo.findByUser(user);

            List<BuyerBookResponse> books = wishlist.stream().map(Wishlist::getBook).map(BuyerBookResponse::new).toList();

            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }




    // Remove from wishlist
    @Override
    public ResponseEntity<String> removeFromWishlist(Long bookId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User user = userRepo.findByEmail(email);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }

            Optional<Wishlist> optionalWishlist = wishlistRepo.findByUserAndBook_BookId(user, bookId);

            if (optionalWishlist.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found in wishlist");
            }

            wishlistRepo.delete(optionalWishlist.get());
            return new ResponseEntity<>("Removed from wishlist", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
