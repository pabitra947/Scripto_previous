package com.example.scripto.controller;

import com.example.scripto.dto.BookResponseDto;
import com.example.scripto.entity.BookListing;
import com.example.scripto.implementation.BookListingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
