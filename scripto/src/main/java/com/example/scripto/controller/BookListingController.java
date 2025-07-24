package com.example.scripto.controller;

import com.example.scripto.dto.BookDto;
import com.example.scripto.dto.EditBookDto;
import com.example.scripto.entity.BookListing;
import com.example.scripto.implementation.BookListingImpl;
import com.example.scripto.response.admin.book.BookResponse;
import com.example.scripto.service.IBookListing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book-seller")
public class BookListingController {

    @Autowired
    private BookListingImpl bookListing;



    //add new book to the DB
    @PostMapping("/add-book")
    public ResponseEntity<BookListing> addNewBook(@RequestBody BookDto book){
        return bookListing.addNewBook(book);
    }


    //Update book price based on the book id
    @PutMapping("/update-book-price&quantity/{id}")
    public ResponseEntity<?> updateBookPriceById(@PathVariable long id, @RequestBody EditBookDto editBookDto){
        return bookListing.updateBookDetails(id, editBookDto);
    }


    //Delete the book using book_id
    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable long id){
        return bookListing.deleteBookById(id);
    }
}
