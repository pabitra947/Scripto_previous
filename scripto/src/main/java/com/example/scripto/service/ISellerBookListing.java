package com.example.scripto.service;


import com.example.scripto.dto.*;
import com.example.scripto.entity.BookListing;
import com.example.scripto.response.seller.book.SellerBookResponseByAuthorName;
import com.example.scripto.response.seller.book.SellerBookResponseByBookName;
import com.example.scripto.response.seller.book.SellerBookResponse;
import com.example.scripto.response.seller.book.SellerBookResponseOnPrice;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISellerBookListing {

    ResponseEntity<BookListing> addNewBook(SellerBookDto information);

    ResponseEntity<BookListing> updateBookDetails(Long bookId, SellerEditBookDto updateDto);

    ResponseEntity<?> deleteBookById(long id);

    ResponseEntity<List<SellerBookResponse>> findAllUniqueBook();

    ResponseEntity<List<SellerBookResponseByBookName>> findBookByBookName(String book);

    ResponseEntity<List<SellerBookResponseByAuthorName>> findBookByAuthorName(String author);

    ResponseEntity<List<SellerBookResponseOnPrice>> findBookByCheaperThanThePrice(Double price);

    ResponseEntity<List<SellerBookResponseOnPrice>> findBookByPriceRange(Double min, Double max);

    ResponseEntity<List<SellerBookResponseOnPrice>> findBookByHigherThenThePrice(Double price);
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