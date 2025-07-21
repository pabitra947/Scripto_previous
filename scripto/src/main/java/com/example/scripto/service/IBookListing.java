package com.example.scripto.service;


import com.example.scripto.dto.*;
import com.example.scripto.entity.BookListing;
import com.example.scripto.response.admin.book.BookResponseByAuthorName;
import com.example.scripto.response.admin.book.BookResponseByBookName;
import com.example.scripto.response.admin.book.BookResponse;
import com.example.scripto.response.admin.book.BookResponseOnPrice;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBookListing {

    ResponseEntity<BookListing> addNewBook(BookDto information);

    ResponseEntity<BookListing> updateBookDetails(Long bookId, EditBookDto updateDto);

    ResponseEntity<?> deleteBookById(long id);

    ResponseEntity<List<BookResponse>> findAllUniqueBook();

    ResponseEntity<List<BookResponseByBookName>> findBookByBookName(String book);

    ResponseEntity<List<BookResponseByAuthorName>> findBookByAuthorName(String author);

    ResponseEntity<List<BookResponseOnPrice>> findBookByCheaperThanThePrice(Double price);

    ResponseEntity<List<BookResponseOnPrice>> findBookByPriceRange(Double min, Double max);

    ResponseEntity<List<BookResponseOnPrice>> findBookByHigherThenThePrice(Double price);
}







//    //Used to update the book price based on the specific book id
//    public ResponseEntity<?> updateBookPriceById(long id, double price) {
//        try {
//            //check book is present on that given id
//            if(!bookListingRepo.existsById(id)){
//                throw new RuntimeException("No book found with ID " + id);
//            }
//
//            //update the book details
//            int updated = bookListingRepo.updateBookPriceById(id, price);
//            if(updated == 0){
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//
//            //find the update book details
//            Optional updateBook = bookListingRepo.findById(id);
//
//            //update the time also
//
//            return new ResponseEntity<>(updateBook, HttpStatus.OK);
//
//        } catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }