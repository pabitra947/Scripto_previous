package com.example.scripto.implementation;

import com.example.scripto.entity.BookListing;
import com.example.scripto.repository.BuyerBookListingRepo;
import com.example.scripto.response.buyer.BuyerBookResponse;
import com.example.scripto.response.buyer.BuyerBookResponseByAuthorName;
import com.example.scripto.response.buyer.BuyerBookResponseByBookName;
import com.example.scripto.service.IBuyerBooksSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyerBookSearchServiceImpl implements IBuyerBooksSearch {

    @Autowired
    private BuyerBookListingRepo buyerBookListingRepo;



    // for getting the all search book
    @Override
    public ResponseEntity<List<BuyerBookResponse>> searchBooks(String keyword) {
        try {
            List<BookListing> availableBooks = buyerBookListingRepo.searchBooks(keyword);

            if (availableBooks.isEmpty()) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }

            List<BuyerBookResponse> response = availableBooks.stream().map(book ->
                    new BuyerBookResponse(
                            book.getBookName(),
                            book.getAuthorName(),
                            book.getPrice(),
                            book.getAvailableQuantity(),
                            book.getBookDetails(),
                            book.getImageUrl()
                    )
            ).collect(Collectors.toList());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }





    // for getting suggestions of book name
    @Override
    public ResponseEntity<List<BuyerBookResponse>> getBookByBookName(String bookName) {
       try {
            List<BookListing> availableBooks =  buyerBookListingRepo.getBookByBookNames(bookName);

           if (availableBooks.isEmpty()) {
               return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
           }

           List<BuyerBookResponse> response = availableBooks.stream().map(book ->
                   new BuyerBookResponse(
                           book.getBookName(),
                           book.getAuthorName(),
                           book.getPrice(),
                           book.getAvailableQuantity(),
                           book.getBookDetails(),
                           book.getImageUrl()
                   )
           ).collect(Collectors.toList());
           return new ResponseEntity<>(response,HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
           return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
       }
    }





     // for getting suggestions of author name
    @Override
    public ResponseEntity<List<BuyerBookResponse>> getBookByAuthorName(String authorName) {
        try{
            List<BookListing> availableBooks = buyerBookListingRepo.suggestAuthorNames(authorName);

            if (availableBooks.isEmpty()) {
                return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
            }

            List<BuyerBookResponse> response = availableBooks.stream().map(book ->
                    new BuyerBookResponse(
                            book.getBookName(),
                            book.getBookName(),
                            book.getPrice(),
                            book.getAvailableQuantity(),
                            book.getBookDetails(),
                            book.getImageUrl()
                    )
            ).collect(Collectors.toList());

            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    }
}
