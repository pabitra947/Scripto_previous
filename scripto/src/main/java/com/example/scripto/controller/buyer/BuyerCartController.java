package com.example.scripto.controller.buyer;

import com.example.scripto.dto.BuyerCartItemDto;
import com.example.scripto.dto.BuyerUpdateCartItemDto;
import com.example.scripto.implementation.BuyerCartServiceImpl;
import com.example.scripto.response.buyer.book.BuyerCartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer-cart")
public class BuyerCartController {


    @Autowired
    private BuyerCartServiceImpl cartService;


    // for add book into cart
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody BuyerCartItemDto request) {
        return cartService.addToCart(request);
    }



    // only buyer see his cart item
    @GetMapping("/see-all-book")
    public ResponseEntity<BuyerCartResponse> viewCart() {
        return cartService.viewCart();
    }



    //update the quantity
    @PutMapping("/update")
    public ResponseEntity<String> updateCartItem(@RequestBody BuyerUpdateCartItemDto updateCartItemDto) {
        return cartService.updateCartItem(updateCartItemDto);
    }


    //delete the book from the cart
    @DeleteMapping("/remove-item")
    public ResponseEntity<String> removeCartItem(@RequestParam Long cartItemId) {
        return cartService.removeCartItem(cartItemId);
    }
}

