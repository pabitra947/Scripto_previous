package com.example.scripto.controller;

import com.example.scripto.response.admin.book.BookResponseByAuthorName;
import com.example.scripto.response.admin.book.BookResponseByBookName;
import com.example.scripto.response.admin.book.BookResponse;
import com.example.scripto.implementation.BookListingImpl;
import com.example.scripto.response.admin.book.BookResponseOnPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller-view")
public class AdminBookView {

    @Autowired
    private BookListingImpl bookListing;



    //Used to get all the unique book(s)
    @GetMapping("/all-unique-book")
    public ResponseEntity<List<BookResponse>> findAllUniqueBook(){
        return bookListing.findAllUniqueBook();
    }


    //Used to get all the book based on the book name(s)
    @GetMapping("/book-by-name")
    public ResponseEntity<List<BookResponseByBookName>> findBookByBookName(@RequestParam String book){
        return bookListing.findBookByBookName(book);
    }


    //Used to get all the book based on the author name
    @GetMapping("/books-by-author")
    public ResponseEntity<List<BookResponseByAuthorName>> findBookByAuthorName(@RequestParam String author){
        return bookListing.findBookByAuthorName(author);
    }



    //Used to find the book lesser then the given price
    @GetMapping("/books-by-cheaper-price")
    public ResponseEntity<List<BookResponseOnPrice>> getBooksByCheaperThenThePrice(@RequestParam Double price){
        return bookListing.findBookByCheaperThanThePrice(price);
    }


    //used to find the book on a particular range
    @GetMapping("/books-by-price-range")
    public ResponseEntity<List<BookResponseOnPrice>> getBooksByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        return bookListing.findBookByPriceRange(min, max);
    }


    //Used to find the book greater then the given price
    @GetMapping("/books-by-higher-price")
    public ResponseEntity<List<BookResponseOnPrice>> getBooksByHigherThenThePrice(@RequestParam Double price){
        return bookListing.findBookByHigherThenThePrice(price);
    }
}
