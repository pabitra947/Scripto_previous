package com.example.scripto.controller.seller;

import com.example.scripto.response.seller.book.SellerBookResponseByAuthorName;
import com.example.scripto.response.seller.book.SellerBookResponseByBookName;
import com.example.scripto.response.seller.book.SellerBookResponse;
import com.example.scripto.implementation.SellerBookListingImpl;
import com.example.scripto.response.seller.book.SellerBookResponseOnPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller-view")
public class SellerBookSearchController {

    @Autowired
    private SellerBookListingImpl bookListing;



    //Used to get all the unique book(s)
    @GetMapping("/all-unique-book")
    public ResponseEntity<List<SellerBookResponse>> findAllUniqueBook(){
        return bookListing.findAllUniqueBook();
    }


    //Used to get all the book based on the book name(s)
    @GetMapping("/book-by-name")
    public ResponseEntity<List<SellerBookResponseByBookName>> findBookByBookName(@RequestParam String book){
        return bookListing.findBookByBookName(book);
    }


    //Used to get all the book based on the author name
    @GetMapping("/books-by-author")
    public ResponseEntity<List<SellerBookResponseByAuthorName>> findBookByAuthorName(@RequestParam String author){
        return bookListing.findBookByAuthorName(author);
    }



    //Used to find the book lesser then the given price
    @GetMapping("/books-by-cheaper-price")
    public ResponseEntity<List<SellerBookResponseOnPrice>> getBooksByCheaperThenThePrice(@RequestParam Double price){
        return bookListing.findBookByCheaperThanThePrice(price);
    }


    //used to find the book on a particular range
    @GetMapping("/books-by-price-range")
    public ResponseEntity<List<SellerBookResponseOnPrice>> getBooksByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return bookListing.findBookByPriceRange(min, max);
    }


    //Used to find the book greater then the given price
    @GetMapping("/books-by-higher-price")
    public ResponseEntity<List<SellerBookResponseOnPrice>> getBooksByHigherThenThePrice(@RequestParam Double price){
        return bookListing.findBookByHigherThenThePrice(price);
    }
}
