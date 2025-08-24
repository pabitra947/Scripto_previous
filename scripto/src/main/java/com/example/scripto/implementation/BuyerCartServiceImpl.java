package com.example.scripto.implementation;

import com.example.scripto.dto.BuyerCartItemDto;
import com.example.scripto.dto.BuyerUpdateCartItemDto;
import com.example.scripto.entity.BookListing;
import com.example.scripto.entity.CartItem;
import com.example.scripto.entity.User;
import com.example.scripto.repository.BuyerBookRepo;
import com.example.scripto.repository.BuyerCartRepo;
import com.example.scripto.repository.UserRepository;
import com.example.scripto.response.buyer.book.BuyerCartItemResponse;
import com.example.scripto.response.buyer.book.BuyerCartResponse;
import com.example.scripto.service.IBuyerCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuyerCartServiceImpl implements IBuyerCart {

    @Autowired
    private BuyerCartRepo buyerCartRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuyerBookRepo buyerBookRepo;


    //add the book into the cart
    @Override
    public ResponseEntity<String> addToCart(BuyerCartItemDto request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User user = userRepository.findByEmail(email);
//            if (user == null || !user.getRole().equalsIgnoreCase("BUYER")) {
//                return ResponseEntity.status(403).body("Access denied: Only buyers can add to cart");
//            }

            Optional<BookListing> optionalBook = buyerBookRepo.findById(request.getBookId());
            if (optionalBook.isEmpty()) {
                return ResponseEntity.badRequest().body("Book not found");
            }

            BookListing book = optionalBook.get();

            // Default quantity = 1 if input is 0 or negative
            int quantity = (request.getQuantity() <= 0) ? 1 : request.getQuantity();

            // Check stock availability
            int available = book.getTotalQuantity() - book.getSoldQuantity();
            if (quantity > available) {
                return ResponseEntity.badRequest().body("Requested quantity exceeds available stock.");
            }

            boolean exists = buyerCartRepo.existsByBuyerAndBook_BookId(user, book.getBookId());
            if (exists) {
                return ResponseEntity.badRequest().body("Book already exists in cart.");
            }

            CartItem cartItem = new CartItem();
            cartItem.setBook(book);
            cartItem.setBuyer(user);
            cartItem.setQuantity(request.getQuantity());

            buyerCartRepo.save(cartItem);

            return new ResponseEntity<>("Book added to cart successfully", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Somethings went wrong try block is not working..");
        }
    }



    //Used to view all the book that is present in the cart
    @Override
    public ResponseEntity<BuyerCartResponse> viewCart() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User buyer = userRepository.findByEmail(email);
//        if (buyer == null || !buyer.getRole().equals("BUYER")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }

        List<CartItem> cartItems = buyerCartRepo.findByBuyer(buyer);

        List<BuyerCartItemResponse> availableItems = cartItems.stream()
                .filter(ci -> ci.getBook().getAvailableQuantity() > 0) //changes
                .map(ci -> {
                    BookListing book = ci.getBook();
                    Double itemTotal = book.getPrice() * ci.getQuantity();
                    return new BuyerCartItemResponse(
                            book.getBookId(),
                            book.getBookName(),
                            book.getAuthorName(),
                            book.getBookDetails(),
                            book.getPrice(),
                            ci.getQuantity(),
                            itemTotal
                    );
                })
                .toList();

        Double totalAmount = availableItems.stream()
                .mapToDouble(BuyerCartItemResponse::getTotalPrice)
                .sum();

        return ResponseEntity.ok(new BuyerCartResponse(availableItems, totalAmount));
    }




    //used to update the book quantity that is already present in the cart
    @Override
    public ResponseEntity<String> updateCartItem(BuyerUpdateCartItemDto updateCartItemDto) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            User buyer = userRepository.findByEmail(email);
//            if (buyer == null || !buyer.getRole().equals("BUYER")) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//            }

            Optional<CartItem> optionalCartItem = buyerCartRepo.findById(updateCartItemDto.getCartItemId());

            if (optionalCartItem.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found");
            }

            CartItem cartItem = optionalCartItem.get();

            // Make sure the item belongs to this buyer
            if (!cartItem.getBuyer().getEmail().equals(buyer.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to update this cart item");
            }

            BookListing book = cartItem.getBook();
            int available = book.getAvailableQuantity();

            if (updateCartItemDto.getQuantity() <= 0) {
                buyerCartRepo.delete(cartItem);
                return ResponseEntity.ok("Cart item removed");
            }

            if (updateCartItemDto.getQuantity() > available) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Only " + available + " items available");
            }
            cartItem.setQuantity(updateCartItemDto.getQuantity());
            buyerCartRepo.save(cartItem);

            return new ResponseEntity<>("Cart item updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while updating cart item: " + e.getMessage());
        }
    }



    //Used to delete the item from the cart
    @Override
    public ResponseEntity<String> removeCartItem(Long cartItemId) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();

            User buyer = userRepository.findByEmail(email);
//            if (buyer == null || !buyer.getRole().equals("BUYER")) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
//            }

            Optional<CartItem> optionalCartItem = buyerCartRepo.findById(cartItemId);

            if (optionalCartItem.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found");
            }

            CartItem cartItem = optionalCartItem.get();

            if (!cartItem.getBuyer().getEmail().equals(buyer.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to delete this cart item");
            }

            buyerCartRepo.delete(cartItem);
            return ResponseEntity.ok("Cart item removed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while removing cart item: " + e.getMessage());
        }
    }
}
