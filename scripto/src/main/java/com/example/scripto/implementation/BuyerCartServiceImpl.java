package com.example.scripto.implementation;

import com.example.scripto.dto.BuyerCartItemDto;
import com.example.scripto.entity.BookListing;
import com.example.scripto.entity.CartItem;
import com.example.scripto.entity.User;
import com.example.scripto.repository.BuyerBookListingRepo;
import com.example.scripto.repository.BuyerCartRepo;
import com.example.scripto.repository.UserRepository;
import com.example.scripto.response.buyer.BuyerCartResponseDto;
import com.example.scripto.service.IBuyerCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BuyerCartServiceImpl implements IBuyerCart {

    @Autowired
    private BuyerCartRepo buyerCartRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuyerBookListingRepo buyerBookListingRepo;

    private BuyerCartItemDto convertToDto(CartItem item) {
        return new BuyerCartItemDto(
                item.getCartItemId(),
                item.getBook().getBookId(),
                item.getBook().getBookName(),
                item.getBook().getAuthorName(),
                item.getBook().getPrice(),
                item.getBook().getAvailableQuantity(),
                item.getBook().getImageUrl(),
                item.getQuantity()
        );
    }




    @Override
    public ResponseEntity<BuyerCartItemDto> addToCart(Long buyerId, Long bookId, int quantity) {
        try {
            User buyer = userRepository.findUserById(buyerId);
            if (buyer == null) throw new RuntimeException("Buyer not found!");

            BookListing book = buyerBookListingRepo.findBookById(bookId);
            if (book == null) throw new RuntimeException("Book not found!");

            CartItem existing = buyerCartRepo.findCartItem(buyerId, bookId);
            if (existing != null) {
                existing.setQuantity(existing.getQuantity() + quantity);
                return new ResponseEntity<>(convertToDto(buyerCartRepo.save(existing)),HttpStatus.OK);
            }

            CartItem newItem = new CartItem();
            newItem.setBuyer(buyer);
            newItem.setBook(book);
            newItem.setQuantity(quantity);
            newItem.setAddedAt(LocalDateTime.now());

            return new ResponseEntity<>(convertToDto(buyerCartRepo.save(newItem)),HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }




    @Override
    public ResponseEntity<BuyerCartResponseDto> getCartItemsWithTotal(Long buyerId) {
        try {
            List<CartItem> items = buyerCartRepo.findAllByBuyerId(buyerId);

            List<BuyerCartItemDto> dtoList = items.stream()
                    .map(this::convertToDto)
                    .toList();

            double totalPrice = dtoList.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();

            return new ResponseEntity<>(new BuyerCartResponseDto(dtoList, totalPrice),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }




    @Override
    public ResponseEntity<String> updateCartItem(Long buyerId, Long bookId, int quantity) {
        try {
            CartItem existing = buyerCartRepo.findCartItem(buyerId, bookId);
            if (existing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item not found in cart!");
            }
            existing.setQuantity(quantity);
            buyerCartRepo.save(existing);
            return ResponseEntity.ok("Cart updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while updating cart item: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> removeCartItem(Long buyerId, Long bookId) {
        try {
            CartItem existing = buyerCartRepo.findCartItem(buyerId, bookId);
            if (existing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item not found in cart!");
            }
            buyerCartRepo.delete(existing);
            return ResponseEntity.ok("Item removed from cart.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while removing cart item: " + e.getMessage());
        }
    }
}
