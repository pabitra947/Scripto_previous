package com.example.scripto.controller;

import com.example.scripto.dto.BuyerCartItemDto;
import com.example.scripto.implementation.BuyerCartServiceImpl;
import com.example.scripto.response.buyer.BuyerCartResponseDto;
import com.example.scripto.service.IBuyerCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer-cart")
public class BuyerCartController {


    @Autowired
    private IBuyerCart cartService;


    // for add book into cart
    @PostMapping("/add-to-cart")
    public ResponseEntity<BuyerCartItemDto> addToCart(
            @RequestParam Long buyerId,
            @RequestParam Long bookId,
            @RequestParam(defaultValue = "1") int quantity) {
        return cartService.addToCart(buyerId, bookId, quantity);
    }



    // only buyer see his cart item
    @GetMapping("/{buyerId}")
    public ResponseEntity<BuyerCartResponseDto> viewCart(@PathVariable Long buyerId) {
        return cartService.getCartItemsWithTotal(buyerId);
    }



    //
    @PutMapping("/update")
    public ResponseEntity<String> updateCartItem(
            @RequestParam Long buyerId,
            @RequestParam Long bookId,
            @RequestParam int quantity) {
        return cartService.updateCartItem(buyerId, bookId, quantity);
    }



    @DeleteMapping("/remove")
    public ResponseEntity<String> removeCartItem(
            @RequestParam Long buyerId,
            @RequestParam Long bookId) {
        return cartService.removeCartItem(buyerId, bookId);
    }
}

