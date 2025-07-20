package com.example.scripto.controller;

import com.example.scripto.dto.BookResponseByBookNameDto;
import com.example.scripto.dto.BookResponseDto;
import com.example.scripto.implementation.BookListingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-view")
public class AdminBookView {

    @Autowired
    private BookListingImpl bookListing;



    //Used to get all the unique book
    @GetMapping("/all-unique-book")
    public ResponseEntity<List<BookResponseDto>> findAllUniqueBook(){
        return bookListing.findAllUniqueBook();
    }


    //Used to get all the book based on the book name
    @GetMapping("/book-by-name")
    public ResponseEntity<List<BookResponseByBookNameDto>> findBookByBookName(@RequestParam String book){
        return bookListing.findBookByBookName(book);
    }
}
