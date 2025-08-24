package com.example.scripto.controller.seller;

import com.example.scripto.dto.SellerBookDto;
import com.example.scripto.dto.SellerEditBookDto;
import com.example.scripto.entity.BookListing;
import com.example.scripto.implementation.SellerBookListingImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book-seller")
public class SellerBookListingController {

    @Autowired
    private SellerBookListingImpl bookListing;



    //add new book to the DB
    @PostMapping("/add-book")
    public ResponseEntity<BookListing> addNewBook(@RequestBody SellerBookDto book){
        return bookListing.addNewBook(book);
    }


    //Update book price based on the book id
    @PutMapping("/update-book-price&quantity/{id}")
    public ResponseEntity<?> updateBookPriceById(@PathVariable long id, @RequestBody SellerEditBookDto sellerEditBookDto){
        return bookListing.updateBookDetails(id, sellerEditBookDto);
    }


    //Delete the book using book_id
    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable long id){
        return bookListing.deleteBookById(id);
    }
}
